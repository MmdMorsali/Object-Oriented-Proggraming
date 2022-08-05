package Controllers;

import DataBase.DataBase;
import View.Controller;
import component.Post;
import component.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ExploreController {
    @FXML
    private GridPane all;

    @FXML
    private VBox down;

    @FXML
    private Label where;

    public GridPane getAll() {
        return all;
    }

    public void setAll(GridPane all) {
        this.all = all;
    }

    public VBox getDown() {
        return down;
    }

    public void setDown(VBox down) {
        this.down = down;
    }

    public Label getWhere() {
        return where;
    }

    public void setWhere(Label where) {
        this.where = where;
    }

    public void startShowPost() throws SQLException, ClassNotFoundException, IOException {

        down.getChildren().clear();



        ArrayList<Post> posts=new ArrayList<>();

        if (where.getText().equals("Explore")) {
            for (Post post : DataBase.getPosts()) {
                if (post.getIsComment().equals("post")) {
                    posts.add(post);
                }
            }
        }else{
            for (Post post : DataBase.getPosts()) {
                if (post.getIsComment().equals("post") && post.getSender().equals(Controller.user)){
                    posts.add(post);
                }
            }

            ArrayList<User> followings = new ArrayList<>();
            followings=Controller.user.getFollowings();

            for (User following : followings) {
                for (Post post : following.getPosts()) {
                    if (post.getIsComment().equals("post")) {
                        posts.add(post);
                    }
                }
            }
        }

        if (posts.size() == 0) {

        } else {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });
            for (int i = 0; i < posts.size(); i++) {
                posts.get(i).addViewToTable(Controller.user,posts.get(i).getPostId(), LocalDateTime.now());
                posts.get(i).getViews().put(Controller.user, LocalDateTime.now());


                FXMLLoader fxmlLoader=new FXMLLoader(PostController.class.getResource("/fxml/Post.fxml"));
                Parent parent=fxmlLoader.load();
                PostController postController=fxmlLoader.getController();
                //Controller.postController=postController;

                //postController.setMyHomePostPageController(this);
                postController.setPost(posts.get(i));
                postController.getAll().getColumnConstraints().get(0).setPercentWidth(100);
                postController.getAll().getColumnConstraints().get(1).setPercentWidth(0);
                postController.getAll().getColumnConstraints().get(2).setPercentWidth(0);
                postController.getUsername().setText(Controller.user.getUserName());
                postController.getNumOfViews().setText("Num Of Views : "+String.valueOf(posts.get(i).getViews().size()));
                postController.getNumofLike().setText("Num Of Likes : "+String.valueOf(posts.get(i).getLikes().size()));
                postController.getNumOfComments().setText("Num Of Comments : "+String.valueOf(posts.get(i).getComments().size()));
                postController.setCommentCounter(posts.get(i).getComments().size());

                postController.getAnalyzePost().setVisible(false);




                if (!Controller.user.getFollowings().contains(posts.get(i).getSender()) ) {
                    postController.getBanCommentOrFollow().setText("Unfollow");
                }



                postController.getUserProfile().setFill(new ImagePattern(new Image(posts.get(i).getSender().getPhotoNameFromImageFolder())));

                //                commentController.getUserProfile().setFill(new ImagePattern(new Image(comment.getSender().getPhotoNameFromImageFolder())));

                if (posts.get(i).getLikes().containsKey(Controller.user)){
                    postController.getLiked().setImage(new Image(getClass().getResource("/images/liked.png").toExternalForm()));
                }







                postController.getTextArea().setMinHeight(24);
                postController.getTextArea().setWrapText(true);
                postController.getTextArea().setText(posts.get(i).getContent());
                postController.getTextArea().setEditable(false);
                if (posts.get(i).getFormat().equalsIgnoreCase("text")){
                    postController.getImagePost().getRowConstraints().get(0).setPercentHeight(0);
                    postController.getImagePost().getRowConstraints().get(1).setPercentHeight(100);
                    postController.getImageOfPostRectangle().setFitHeight(0);
                    postController.getImageOfPostRectangle().setFitWidth(0);
                }else{
                    if (posts.get(i).getContent()==null){
                        postController.getImagePost().getRowConstraints().get(0).setPercentHeight(100);
                        postController.getImagePost().getRowConstraints().get(1).setPercentHeight(0);
                    }else{
                        postController.getImagePost().getRowConstraints().get(0).setPercentHeight(60);
                        postController.getImagePost().getRowConstraints().get(1).setPercentHeight(40);
                    }

                    System.out.println(posts.get(i).getPhotoAddress());

                    postController.getImageOfPostRectangle().setFitHeight(300);
                    postController.getImageOfPostRectangle().setFitWidth(300);
                    postController.getImageOfPostRectangle().setPreserveRatio(true);
                    postController.getImageOfPostRectangle().setImage(new Image(posts.get(i).getPhotoAddress()));
                    postController.getImageOfPostRectangle().setPreserveRatio(true);



                }
                if (posts.get(i).getContent().length()<1000)
                    postController.initializer();







                down.getChildren().add(parent);


            }



        }



    }

    public void back(MouseEvent mouseEvent) {
    }
}