package com.example.biservice.repository.cart;

import com.example.biservice.entity.cart.CartItem;
import com.example.biservice.entity.cart.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, CartItemId> {

    List<CartItem> findAllByIdCartId(Long cartId);

    Optional<CartItem> findByIdCartIdAndIdProductId(Long cartId, Integer productId);

    @Modifying
    @Transactional
    @Query(value = "delete from cart_item where cart_id = :cartId", nativeQuery = true)
    void deleteAllByIdCartId(@Param("cartId") Long cartId);
}
