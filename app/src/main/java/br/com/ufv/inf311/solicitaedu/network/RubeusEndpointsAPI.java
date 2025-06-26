package br.com.ufv.inf311.solicitaedu.network;

import br.com.ufv.inf311.solicitaedu.model.Contact;
import br.com.ufv.inf311.solicitaedu.model.ContactDTO;
import br.com.ufv.inf311.solicitaedu.model.RegisterDTO;
import br.com.ufv.inf311.solicitaedu.model.Request;
import br.com.ufv.inf311.solicitaedu.model.RequestDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RubeusEndpointsAPI {
    @POST("/api/Contato/dadosPessoas")
    Call<ContactDTO> getContact(@Body Contact contact, @Query("origem") String origem, @Query("token") String token);

    @POST("api/Contato/listarOportunidades")
    Call<RegisterDTO> getRegisters(@Query("origem") String origem, @Query("token") String token, @Query("id") String id);

    @POST("/api/Evento/cadastro")
    Call<RequestDTO> createRequest(@Body Request request, @Query("origem") String origim, @Query("token") String token);
}
