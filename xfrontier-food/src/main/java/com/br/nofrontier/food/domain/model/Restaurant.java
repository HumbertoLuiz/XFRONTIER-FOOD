package com.br.nofrontier.food.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@JsonNaming(SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class Restaurant {

	@EqualsAndHashCode.Include
	@ToString.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(name = "shipping_rate", nullable = false)
	private BigDecimal shippingRate;

	@JsonIgnore
	@JsonBackReference
	@ManyToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "kitchen_id", nullable = false)
	private Kitchen kitchen;

	@JsonIgnore
	@Embedded
	private Address address;

	private Boolean active = Boolean.TRUE;

	private Boolean open = Boolean.FALSE;

	@CreationTimestamp
	@Column(nullable = false, name = "register_date", columnDefinition = "datetime")
	private OffsetDateTime registerDate;

	@UpdateTimestamp
	@Column(nullable = false, name = "update_date", columnDefinition = "datetime")
	private OffsetDateTime updateDate;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "restaurant_payment_method", joinColumns = @JoinColumn(name = "restaurant_id"), inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
	private Set<PaymentMethod> paymentMethods = new HashSet<>();

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "restaurant_user_responsible", joinColumns = @JoinColumn(name = "restaurant_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<UserEntity> responsible = new HashSet<>();

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurants")
	private List<Product> products = new ArrayList<>();

	public void active() {
		setActive(true);
	}

	public void inactivar() {
		setActive(false);
	}

	public void open() {
		setOpen(true);
	}

	public void close() {
		setOpen(false);
	}

	public boolean isOpen() {
		return this.open;
	}

	public boolean isClose() {
		return !isOpen();
	}

	public boolean isInactive() {
		return !isActive();
	}

	public boolean isActive() {
		return this.active;
	}

	public boolean openingAllowed() {
		return isActive() && isOpen();
	}

	public boolean activationAllowed() {
		return isInactive();
	}

	public boolean inactivationPermitted() {
		return isActive();
	}

	public boolean fechamentoPermitido() {
		return isOpen();
	}

	public boolean removerFormaPagamento(PaymentMethod paymentMethods) {
		return getPaymentMethods().remove(paymentMethods);
	}

	public boolean adicionarFormaPagamento(PaymentMethod paymentMethods) {
		return getPaymentMethods().add(paymentMethods);
	}

	public boolean aceitaFormaPagamento(PaymentMethod paymentMethods) {
		return getPaymentMethods().contains(paymentMethods);
	}

	public boolean naoAceitaFormaPagamento(PaymentMethod paymentMethods) {
		return !aceitaFormaPagamento(paymentMethods);
	}

	public boolean removeResponsible(UserEntity user) {
		return getResponsible().remove(user);
	}

	public boolean addResponsible(UserEntity user) {
		return getResponsible().add(user);
	}

}
