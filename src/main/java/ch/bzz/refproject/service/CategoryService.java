package ch.bzz.refproject.service;

import ch.bzz.refproject.data.CategoryDao;
import ch.bzz.refproject.data.Dao;
import ch.bzz.refproject.model.Category;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * services for categories
 * <p>
 * M151: Refproject
 *
 * @author Marcel Suter
 */
@Path("category")
public class CategoryService {

    /**
     * produces a list of all categorys
     *
     * @param token encrypted authorization token
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listCategories(
            @CookieParam("token") String token
    ) {

        Dao<Category, String> categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.getAll();

        if (categoryList.isEmpty()) {
            return Response
                    .status(404)
                    .entity("{\"error\":\"Keine Kategorien gefunden\"}")
                    .build();
        } else {
            return Response
                    .status(200)
                    .entity(categoryList)
                    .build();
        }
    }

    /**
     * reads a single category identified by the categoryId
     *
     * @param categoryUUID the categoryUUID in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

    public Response readCategory(
            @QueryParam("uuid") String categoryUUID,
            @CookieParam("token") String token
    ) {
        Dao<Category, String> categoryDAO = new CategoryDao();
        Category category = categoryDAO.getEntity(categoryUUID);
        if (category.getTitle() == null) {
             return Response
                    .status(404)
                    .entity("{\"error\":\"Keine Kategorie gefunden\"}")
                    .build();
        } else {
            return Response
                    .status(200)
                    .entity(category)
                    .build();
        }
    }
    
}
