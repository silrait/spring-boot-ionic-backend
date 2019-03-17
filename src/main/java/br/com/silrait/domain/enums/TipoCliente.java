package br.com.silrait.domain.enums;

import java.util.stream.Stream;

public enum TipoCliente {
	PESSOAFISICA(1, "Pessoa Física"), PESSOAJURIDICA(2, "Pessoa Jurídica");

	private Integer cod;
	private String descricao;

	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoCliente toEnum(Integer cod) {
		if (cod == null)
			return null;

		return Stream.of(TipoCliente.values()).filter(tipoCliente -> tipoCliente.getCod().equals(cod)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Id inválido: " + cod));
	}
}
