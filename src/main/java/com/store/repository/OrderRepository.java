package com.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.entities.Order;
import com.store.entities.User;

public interface OrderRepository extends JpaRepository<Order, String> {

	List<Order> findByUser(User user);



}
