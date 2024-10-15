package com.br.nofrontier.food.domain.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailySales {

	private Date date;
	private Long totalSales;
	private BigDecimal totalBilled;

}
