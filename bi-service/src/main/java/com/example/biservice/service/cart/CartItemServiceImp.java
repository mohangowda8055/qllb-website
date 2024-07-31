package com.example.biservice.service.cart;

import com.example.biservice.entity.Product;
import com.example.biservice.entity.cart.Cart;
import com.example.biservice.entity.cart.CartItem;
import com.example.biservice.entity.cart.CartItemId;
import com.example.biservice.exception.QuantityException;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.payload.cart.CartItemDto;
import com.example.biservice.payload.cart.CartItemIdDto;
import com.example.biservice.payload.cart.CartItemResponseDto;
import com.example.biservice.repository.ProductRepo;
import com.example.biservice.repository.cart.CartItemRepo;
import com.example.biservice.repository.cart.CartRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemServiceImp implements CartItemService {

    private final ModelMapper modelMapper;

    private final CartItemRepo cartItemRepo;

    private final CartRepo cartRepo;

    private final ProductRepo productRepo;

    private final CartService cartService;


    @Override
    public CartItemDto createCartItem(CartItemDto cartItemDto, Long cartId, Integer productId) {
        Optional<CartItem> optionalCartItem = this.cartItemRepo.findByIdCartIdAndIdProductId(cartId,productId);
        if(optionalCartItem.isPresent()){
            int quantity = optionalCartItem.get().getQuantity() + cartItemDto.getQuantity();
            CartItemIdDto cartItemIdDto = new CartItemIdDto(cartId,productId);
            cartItemDto.setId(cartItemIdDto);
            cartItemDto.setQuantity(quantity);
          return this.updateCartItem(cartItemDto,cartId,productId);
        }else {
            Cart cart = this.cartRepo.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cannot create cart item - no existing cart found with id " + cartId));
            Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Cannot create cart item - no existing product found with id " + productId));
            CartItem cartItem = this.modelMapper.map(cartItemDto, CartItem.class);
            if(!(cartItem.getQuantity() >= 1 && cartItem.getQuantity() <= product.getStock())){
                throw new QuantityException(cartItem.getQuantity() < 1 ? "quantity cannot be below one" : "quantity exceeds stock available");
            }
            CartItemId cartItemId = new CartItemId(cartId, productId);
            cartItem.setId(cartItemId);
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            CartItem savedCartItem = this.cartItemRepo.save(cartItem);
            this.cartService.updateCart(cart.getUser().getUserId());
            return this.modelMapper.map(savedCartItem, CartItemDto.class);
        }
    }

    @Override
    public List<CartItemResponseDto> getAllCartItems(Long cartId) {
        List<CartItem> cartItems = this.cartItemRepo.findAllByIdCartId(cartId);
        return cartItems.stream().map(cartItem -> this
                .modelMapper.map(cartItem, CartItemResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public CartItemDto getCartItem(Long cartId, Integer productId) {
        CartItem cartItem = this.cartItemRepo.findByIdCartIdAndIdProductId(cartId, productId).orElseThrow(()->new ResourceNotFoundException("Cannot find cart item - no existing cart or product found"));
        return this.modelMapper.map(cartItem,CartItemDto.class);
    }

    @Override
    public CartItemDto updateCartItem(CartItemDto cartItemDto, Long cartId, Integer productId) {
        Optional<CartItem> optionalCartItem = this.cartItemRepo.findByIdCartIdAndIdProductId(cartId, productId);
        if(optionalCartItem.isPresent()){
            CartItem cartItem = this.modelMapper.map(cartItemDto, CartItem.class);
            if(!(cartItem.getQuantity() >= 1 && cartItem.getQuantity() <= optionalCartItem.get().getProduct().getStock())){
                throw new QuantityException(cartItem.getQuantity() < 1 ? "quantity cannot be below one" : "quantity exceeds stock available");
            }
            cartItem.setProduct(optionalCartItem.get().getProduct());
            cartItem.setCart(optionalCartItem.get().getCart());
            CartItem updatedCartItem = this.cartItemRepo.save(cartItem);
            this.cartService.updateCart(optionalCartItem.get().getCart().getUser().getUserId());
            return this.modelMapper.map(updatedCartItem, CartItemDto.class);
        }else throw new ResourceNotFoundException("Cannot update cart item - no existing cart or product found with id "+productId);
    }

    @Override
    public void deleteCartItem(Long cartId, Integer productId) {
        CartItem cartItem = this.cartItemRepo.findByIdCartIdAndIdProductId(cartId, productId).orElseThrow(()->new ResourceNotFoundException("Cannot delete cart item - no existing cart or product found"));
        this.cartItemRepo.delete(cartItem);
        this.cartService.updateCart(cartItem.getCart().getUser().getUserId());
    }

    @Override
    public void deleteAllCartItems(Long cartId) {
        Cart cart = this.cartRepo.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cannot delete cart items - no existing cart found with cart id "+cartId));
        this.cartItemRepo.deleteAllByIdCartId(cart.getCartId());
        this.cartService.updateCart(cart.getUser().getUserId());
    }
}
