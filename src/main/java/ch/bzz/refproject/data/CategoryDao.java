package ch.bzz.refproject.data;

import ch.bzz.refproject.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * data access for category entity
 * <p>
 * M151: Refproject
 *
 * @author Marcel Suter
 */
public class CategoryDao implements Dao<Category, String> {

    /**
     * reads all categories in the table "Category"
     *
     * @return list of categories
     */
    @Override
    public List<Category> getAll() {
        List<Category> categoryList = new ArrayList<>();
        String sqlQuery = "SELECT categoryUUID, title" +
                " FROM Category" +
                " ORDER BY title";
        try {
            ResultSet resultSet = MySqlDB.sqlSelect(sqlQuery);
            while (resultSet.next()) {
                Category category = new Category();
                setValues(resultSet, category);
                categoryList.add(category);
            }
        } catch (SQLException sqlEx) {
            MySqlDB.printSQLException(sqlEx);
            throw new RuntimeException();
        } finally {

            MySqlDB.sqlClose();
        }

        return categoryList;
    }

    /**
     * reads a category in the table "Category" identified by the categoryUUID
     *
     * @return list of categories
     */
    @Override
    public Category getEntity(String categoryUUID) {
        Category category = new Category();
        String sqlQuery = "SELECT categoryUUID, title" +
                " FROM Category" +
                " WHERE categoryUUID=?";
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, categoryUUID);
        try {
            ResultSet resultSet = MySqlDB.sqlSelect(sqlQuery, values);
            if (resultSet.next()) {
                setValues(resultSet, category);
            }
        } catch (SQLException sqlEx) {
            MySqlDB.printSQLException(sqlEx);
            throw new RuntimeException();
        } finally {

            MySqlDB.sqlClose();
        }

        return category;
    }

    /**
     * sets the values of the attributes from the resultset
     *
     * @param resultSet the resultSet with an entity
     * @param category      a category object
     * @throws SQLException
     */
    private void setValues(ResultSet resultSet, Category category) throws SQLException {
        category.setCategoryUUID(resultSet.getString("categoryUUID"));
        category.setTitle(resultSet.getString("title"));
    }
}
