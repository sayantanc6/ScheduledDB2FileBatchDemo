package dummy;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CoffeeMapper implements RowMapper<Coffee> {

	@Override
	public Coffee mapRow(ResultSet rs, int rowNum) throws SQLException {
		Coffee coffee = new Coffee();
		coffee.setCoffee_id(rs.getInt("coffee_id")); 
        coffee.setBrand(rs.getString("brand"));
        coffee.setCharacteristics(rs.getString("characteristics"));
        coffee.setOrigin(rs.getString("origin")); 
        return coffee;
	}

}
