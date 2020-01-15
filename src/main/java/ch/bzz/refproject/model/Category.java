package ch.bzz.refproject.model;
/**
 * the category a project is assigned to
 * <p>
 * M151: Refproject
 *
 * @author Marcel Suter
 */
public class Category {
    private String categoryUUID;
    private String title;

    /**
     * Gets the categoryUUID
     *
     * @return value of categoryUUID
     */
    public String getCategoryUUID() {
        return categoryUUID;
    }

    /**
     * Sets the categoryUUID
     *
     * @param categoryUUID the value to set
     */

    public void setCategoryUUID(String categoryUUID) {
        this.categoryUUID = categoryUUID;
    }

    /**
     * Gets the title
     *
     * @return value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title
     *
     * @param title the value to set
     */

    public void setTitle(String title) {
        this.title = title;
    }
}
