package com.example.biservice.repository.order;

import com.example.biservice.entity.order.Order;
import com.example.biservice.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    Page<Order> findAllByUser(User user, Pageable pageable);
}
