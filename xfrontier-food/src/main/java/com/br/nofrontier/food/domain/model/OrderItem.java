package com.br.nofrontier.food.domain.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class OrderItem {

	@EqualsAndHashCode.Include
	@ToString.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private BigDecimal unitPrice;
	private BigDecimal totalPrice;
	private Integer quantity;
	private String observation;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order orders;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product products;

	public void calculateTotalPrice() {
		BigDecimal unitPrice = this.getUnitPrice();
		Integer quantity = this.getQuantity();

		if (unitPrice == null) {
			unitPrice = BigDecimal.ZERO;
		}

		if (quantity == null) {
			quantity = 0;
		}

		this.setTotalPrice(unitPrice.multiply(new BigDecimal(quantity)));
	}

}
