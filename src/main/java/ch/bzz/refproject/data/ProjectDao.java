package ch.bzz.refproject.data;

import ch.bzz.refproject.model.Category;
import ch.bzz.refproject.model.Project;
import ch.bzz.refproject.util.Result;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDao implements Dao<Project, String>{

    @Override
    public List<Project> getAll(){
        ResultSet resultSet;
        List<Project> projectList = new ArrayList<>();
        String sqlQuery =
                "SELECT c.category, p.title, p.startDate, p.endDate, p.isbn," +
                        "  FROM Project AS p JOIN Category AS c USING (categoryUUID)" +
                        "  ORDER BY title" +
                        "  WHERE status=\"A\"";
        try {
            resultSet = MySqlDB.sqlSelect(sqlQuery);
            while (resultSet.next()) {
                Project project = new Project();
                setValues(resultSet, project);
                projectList.add(project);
            }

        } catch (SQLException sqlEx) {

            sqlEx.printStackTrace();
            throw new RuntimeException();
        } finally {

            MySqlDB.sqlClose();
        }
        return projectList;
    }

    /**
     * reads a project from the table "Project" identified by the projectUUID
     * @param projectUUID the primary key
     * @return project object
     */
    @Override
    public Project getEntity(String projectUUID) {
        Connection connection;
        PreparedStatement prepStmt;
        ResultSet resultSet;
        Project project = new Project();

        String sqlQuery =
                "SELECT p.title, c.category, p.endDate, p.startDate, p.isbn, " +
                        "  FROM Project AS p JOIN Category AS c USING (categoryUUID)" +
                        " WHERE projectUUID='" + projectUUID.toString() + "'";
        try {
            connection = MySqlDB.getConnection();
            prepStmt = connection.prepareStatement(sqlQuery);
            resultSet = prepStmt.executeQuery();
            if (resultSet.next()) {
                setValues(resultSet, project);
            }

        } catch (SQLException sqlEx) {

            sqlEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            MySqlDB.sqlClose();
        }
        return project;

    }

    /**
     * deletes a project in the table "Project" identified by the projectUUID
     * @param projectUUID the primary key
     * @return Result code
     */
    @Override
    public Result delete(String projectUUID) {
        Connection connection;
        PreparedStatement prepStmt;
        String sqlQuery =
                "DELETE FROM Project" +
                        " WHERE projectUUID='" + projectUUID.toString() + "'";
        try {
            connection = MySqlDB.getConnection();
            prepStmt = connection.prepareStatement(sqlQuery);
            int affectedRows = prepStmt.executeUpdate();
            if (affectedRows == 1) {
                return Result.SUCCESS;
            } else if (affectedRows == 0) {
                return Result.NOACTION;
            } else {
                return Result.ERROR;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException();
        }

    }


    /**
     * saves a project in the table "Project"
     * @param project the project object
     * @return Result code
     */
    @Override
    public Result save(Project project) {
        Connection connection;
        PreparedStatement prepStmt;
        String sqlQuery =
                "REPLACE Project" +
                        " SET projectUUID='" + project.getProjectUUID() + "'," +
                        " title='" + project.getTitle() + "'," +
                        " startDate='" + project.getStartDate() + "'," +
                        " endDate='" + project.getEndDate() + "'," +
                        " status=" + project.getStatus() + "," +
                        " categoryUUID='" + project.getCategory().getCategoryUUID() + "'";
        try {
            connection = MySqlDB.getConnection();
            prepStmt = connection.prepareStatement(sqlQuery);
            int affectedRows = prepStmt.executeUpdate();
            if (affectedRows <= 2) {
                return Result.SUCCESS;
            } else if (affectedRows == 0) {
                return Result.NOACTION;
            } else {
                return Result.ERROR;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException();
        }

    }


    /**
            * sets the values of the attributes from the resultset
     *
             * @param resultSet the resultSet with an entity
     * @param project      a project object
     * @throws SQLException
     */
    private void setValues(ResultSet resultSet, Project project) throws SQLException{
        project.setProjectUUID(resultSet.getString("projectUUID"));
        project.setTitle(resultSet.getString("title"));
        project.setEndDate(resultSet.getString("endDate"));
        project.setStartDate(resultSet.getString("startDate"));
        project.setCategory(new Category());
        project.getCategory().setCategoryUUID(resultSet.getString("categoryUUID"));
    }

    /**
     * default constructor
     */
    public ProjectDao() {}
}
