package com.br.nofrontier.food.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.br.nofrontier.food.domain.enums.OrderStatus;
import com.br.nofrontier.food.domain.event.OrderCancelledEvent;
import com.br.nofrontier.food.domain.event.OrderConfirmedEvent;
import com.br.nofrontier.food.domain.exception.BusinessException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "order_entity")
public class Order extends AbstractAggregateRoot<Order> {

	@EqualsAndHashCode.Include
	@ToString.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String code;

	private BigDecimal subtotal;
	private BigDecimal shippingRate;
	private BigDecimal TotalValue;

	@Embedded
	private Address shippingAddress;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(10)")
	private OrderStatus status = OrderStatus.CREATED;

	@CreationTimestamp
	private OffsetDateTime creationDate;

	private OffsetDateTime confirmationDate;
	private OffsetDateTime cancellationDate;
	private OffsetDateTime deliveryDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_method_id", nullable = false)
	private PaymentMethod paymentMethods;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurants;

	@ManyToOne
	@JoinColumn(name = "user_customer_id", nullable = false)
	private UserEntity customer;

	@OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
	private List<OrderItem> items = new ArrayList<>();

	public void calculateTotalValue() {
		getItems().forEach(OrderItem::calculateTotalPrice);

		this.subtotal = getItems().stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

		this.TotalValue = this.subtotal.add(this.shippingRate);
	}

	public void confirm() {
		setStatus(OrderStatus.CONFIRMED);
		setConfirmationDate(OffsetDateTime.now());

		registerEvent(new OrderConfirmedEvent(this));
	}

	public void entregar() {
		setStatus(OrderStatus.DELIVERED);
		setDeliveryDate(OffsetDateTime.now());
	}

	public void cancelar() {
		setStatus(OrderStatus.CANCELED);
		setCancellationDate(OffsetDateTime.now());

		registerEvent(new OrderCancelledEvent(this));
	}

	public boolean podeSerConfirmado() {
		return getStatus().canChangeTo(OrderStatus.CONFIRMED);
	}

	public boolean podeSerEntregue() {
		return getStatus().canChangeTo(OrderStatus.DELIVERED);
	}

	public boolean podeSerCancelado() {
		return getStatus().canChangeTo(OrderStatus.CANCELED);
	}

	private void setStatus(OrderStatus newStatus) {
		if (getStatus().cannotChangeTo(newStatus)) {
			throw new BusinessException(String.format("Status order %s cannot be changed from %s to %s", getCode(),
					getStatus().getDescription(), newStatus.getDescription()));
		}

		this.status = newStatus;
	}

	@PrePersist
	private void generateCode() {
		setCode(UUID.randomUUID().toString());
	}

}
