package com.br.nofrontier.food.domain.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "group_entity")
public class Group {

	@EqualsAndHashCode.Include
	@ToString.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "group_permission", joinColumns = { @JoinColumn(name = "group_id") }, inverseJoinColumns = {
			@JoinColumn(name = "permission_id") })
	private Set<Permission> permissions = new HashSet<>();


	public boolean removePermission(Permission permission) {
		return getPermissions().remove(permission);
	}

	public boolean addPermission(Permission permission) {
		return getPermissions().add(permission);
	}

}