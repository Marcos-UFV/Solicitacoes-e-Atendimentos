package br.com.ufv.inf311.solicitaedu.network;

import br.com.ufv.inf311.solicitaedu.model.Contact;
import br.com.ufv.inf311.solicitaedu.model.ContactDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RubeusEndpointsAPI {
    @POST("/api/Contato/dadosPessoas")
    Call<ContactDTO> getContact(@Body Contact contact, @Query("origem") String origem, @Query("token") String token);
}
