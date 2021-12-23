package br.com.boavista.updatebatchgcp.writer;

import br.com.boavista.updatebatchgcp.dominio.Titulo;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Configuration
public class JdbcArquivoWriterConfig {


	@Bean
	public JdbcBatchItemWriter<Titulo> jdbcBatchItemWriter(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Titulo>()
				.dataSource(dataSource)
				.sql("update protesto set uf_devedor = 'SP' " +
					" where documento = ? " +
					"   and tipo_documento = ? " +
					"   and  uf_devedor is null ")
				.itemPreparedStatementSetter(itemPreparedStatementsetter())
				.build();
	}

	private ItemPreparedStatementSetter<Titulo> itemPreparedStatementsetter() {

		return new ItemPreparedStatementSetter<Titulo>() {
			@Override
			public void setValues(Titulo titulo, PreparedStatement ps) throws SQLException {

				ps.setString(1, titulo.getDocumento());
				ps.setInt(2, titulo.getTipoDocumento());

			}
		};
	}
}
