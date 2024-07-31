package com.example.biservice.service.cart;

import com.example.biservice.entity.cart.CartItem;
import com.example.biservice.entity.user.User;
import com.example.biservice.entity.cart.Cart;
import com.example.biservice.exception.QuantityException;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.payload.ProductResponseDto;
import com.example.biservice.payload.cart.CartDto;
import com.example.biservice.payload.cart.CartItemResponseDto;
import com.example.biservice.payload.cart.CartResponseDto;
import com.example.biservice.repository.cart.CartItemRepo;
import com.example.biservice.repository.cart.CartRepo;
import com.example.biservice.repository.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService{

    private final ModelMapper modelMapper;

    private final CartRepo cartRepo;

    private final UserRepo userRepo;

    private final CartItemRepo cartItemRepo;


    @Override
    public CartDto createCart(Long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("Cannot create cart - no existing user found with id "+userId));
        Cart cart = Cart.builder()
                .deliveryCost(0)
                .total(0)
                .user(user)
                .build();
        Cart savedCart = this.cartRepo.save(cart);
        return this.modelMapper.map(savedCart, CartDto.class);
    }

    @Override
    public CartResponseDto getCartByUser(Long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("Cannot find cart - no existing user found with id "+userId));
        Cart cart = this.cartRepo.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Cannot find cart - no existing cart found with user id "+userId));
        CartResponseDto cartResponseDto = this.modelMapper.map(cart, CartResponseDto.class);

        List<CartItemResponseDto> cartItemResponseDtos = cart.getCartItems().stream()
                .map(cartItem -> {
                    CartItemResponseDto cartItemResponseDto = this.modelMapper.map(cartItem, CartItemResponseDto.class);
                    ProductResponseDto productDto = this.modelMapper.map(cartItem.getProduct(), ProductResponseDto.class);
                    cartItemResponseDto.setProduct(productDto);
                    return cartItemResponseDto;
                })
                .collect(Collectors.toList());
        cartResponseDto.setCartItems(cartItemResponseDtos);
        return cartResponseDto;
    }

    @Override
    public CartDto updateCart(Long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("Cannot find cart - no existing user found with id "+userId));
        Optional<Cart> optionalCart = this.cartRepo.findByUser(user);
        if(optionalCart.isPresent()){
            List<CartItem> cartItems = this.cartItemRepo.findAllByIdCartId(optionalCart.get().getCartId());
            Cart cart = Cart.builder()
                    .cartId(optionalCart.get().getCartId())
                    .total(cartItems.isEmpty() ? 0 : calculateTotal(cartItems))
                    .deliveryCost(0)
                    .pincode(optionalCart.get().getPincode())
                    .user(user)
                    .build();
            Cart updatedCart = this.cartRepo.save(cart);
            return this.modelMapper.map(updatedCart, CartDto.class);
        }else{
            throw new ResourceNotFoundException("Cannot update cart - no existing cart found with user id "+userId);
        }
   }

   @Override
   public CartDto updateCartWithItems(Long userId, CartResponseDto cartResponseDto){
        List<CartItemResponseDto> cartItemResponseDtoList = cartResponseDto.getCartItems();
        for(CartItemResponseDto cartItemDto : cartItemResponseDtoList ){
            Optional<CartItem> optionalCartItem = cartItemRepo.findByIdCartIdAndIdProductId(cartItemDto.getId().getCartId(), cartItemDto.getId().getProductId());
            if(optionalCartItem.isPresent()){
                CartItem cartItem = optionalCartItem.get();
                if(!(cartItemDto.getQuantity() >= 1 && cartItemDto.getQuantity() <= optionalCartItem.get().getProduct().getStock())){
                    throw new QuantityException(cartItemDto.getQuantity() < 1 ? "quantity cannot be below one" : "quantity exceeds stock available");
                }
                cartItem.setQuantity(cartItemDto.getQuantity());
                cartItem.setRebate(cartItemDto.isRebate());
                this.cartItemRepo.save(cartItem);
            }else {
                throw new ResourceNotFoundException("Cannot update cart - no existing product found with id "+ cartItemDto.getId().getProductId());
            }
        }
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("Cannot update cart - no existing user found with id "+userId));
        Optional<Cart> optionalCart = this.cartRepo.findByUser(user);
        if(optionalCart.isPresent()){
           List<CartItem> cartItems = this.cartItemRepo.findAllByIdCartId(optionalCart.get().getCartId());
           Cart cart = Cart.builder()
                   .cartId(optionalCart.get().getCartId())
                   .total(cartItems.isEmpty() ? 0 : calculateTotal(cartItems))
                   .deliveryCost(0)
                   .pincode(optionalCart.get().getPincode())
                   .user(user)
                   .build();
           Cart updatedCart = this.cartRepo.save(cart);
           return this.modelMapper.map(updatedCart, CartDto.class);
       }else{
           throw new ResourceNotFoundException("Cannot update cart - no existing cart found with user id "+userId);
       }
   }

    @Override
    public CartDto updatePincode(Long userId, CartDto cartDto) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("Cannot update cart - no existing user found with id "+userId));
        Cart cart = this.cartRepo.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Cannot update cart - no existing cart found with userId "+userId));
        cart.setPincode(cartDto.getPincode());
        Cart updatedCart = this.cartRepo.save(cart);
        return this.modelMapper.map(updatedCart, CartDto.class);
    }

    private float calculateTotal(List<CartItem> cartItems){
       return cartItems.stream().map(cartItem -> {
            float mrp = cartItem.getProduct().getMrp();
            float discount = cartItem.getProduct().getDiscountPercentage();
            return (cartItem.getQuantity()) * ((mrp * (1 - (discount / 100))) - (cartItem.isRebate() ? cartItem.getProduct().getRebate() : 0));
        }).reduce(0.0000f, Float::sum);
    }
}
