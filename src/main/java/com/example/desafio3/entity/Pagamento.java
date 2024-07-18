package com.example.desafio3.entity;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "pagamentos")
public class Pagamento {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Instant dataPagamento;

	private boolean pago; 

	@JsonIgnore
	@OneToOne
	@MapsId
	private Venda venda;
}
