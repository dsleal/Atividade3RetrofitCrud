package contrato.com.atividade3retrofitcrud.resoure;

import java.util.List;

import contrato.com.atividade3retrofitcrud.model.Users;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserResource {
    @GET("users")
    Call<List<Users>> get();

    @GET("users/{id}")
    Call<Users> getPorId(@Path("id") Integer id);

    @POST("users")
    Call<Users> post(@Body Users users);

    @PUT("users/{id}")
    Call<Users> put(@Path("id") Integer id, @Body Users users);

    @DELETE("/users/{id}")
    Call<Void> delete(@Path("id") Integer id);

    @PATCH("/users/{id}")
    Call<Users> patch(Integer id, @Body Users users);

}
