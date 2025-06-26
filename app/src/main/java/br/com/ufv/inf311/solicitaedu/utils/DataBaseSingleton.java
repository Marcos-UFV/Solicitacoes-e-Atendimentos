package br.com.ufv.inf311.solicitaedu.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public final class DataBaseSingleton {
    protected SQLiteDatabase db;
    private final String DB_NAME = "SOLICITA_db";
    private static DataBaseSingleton INSTANCE;
    private final String[] SCRIPT_DATABASE_CREATE = new String[] {
            "CREATE TABLE Notification (id INTEGER PRIMARY KEY AUTOINCREMENT, lastSeenID INTEGER); ",

            "CREATE TABLE Login (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, senha TEXT, lastLogin TEXT);",
            "INSERT INTO Login (email, senha, lastLogin) VALUES (NULL, NULL, '01/01/1900');"};

    private DataBaseSingleton(Context ctx) {
        // Abre o banco de dados já existente ou então cria um banco novo
        db = ctx.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);

        // busca por tabelas existentes no banco igual "show tables" do MySQL
        // SELECT * FROM sqlite_master WHERE type = "table"
        Cursor c = search("sqlite_master", null, "type = 'table'", "");

        // Cria tabelas do banco de dados caso o mesmo estiver vazio.
        if(c.getCount() == 1){
            for(int i = 0; i < SCRIPT_DATABASE_CREATE.length; i++){
                db.execSQL(SCRIPT_DATABASE_CREATE[i]);
            } Log.i("BANCO_DADOS", "Criou tabelas do banco e as populou.");
        }

        c.close();
        Log.i("BANCO_DADOS", "Abriu conexão com o banco.");
    }

    // Insere um novo registro
    public long insert(String tabela, ContentValues valores) {
        long id = db.insert(tabela, null, valores);

        Log.i("BANCO_DADOS", "Cadastrou registro com o id [" + id + "]");
        return id;
    }

    // Atualiza registros
    public int update(String tabela, ContentValues valores, String where) {
        int count = db.update(tabela, valores, where, null);
        Log.i("BANCO_DADOS", "Atualizou [" + count + "] registros");
        return count;
    }

    // Deleta registros
    public int delete(String tabela, String where) {
        int count = db.delete(tabela, where, null);
        Log.i("BANCO_DADOS", "Deletou [" + count + "] registros");
        return count;
    }

    // Busca registros
    public Cursor search(String tabela, String colunas[], String where, String orderBy) {
        Cursor c;
        if(!where.equals(""))
            c = db.query(tabela, colunas, where, null, null, null, orderBy);
        else
            c = db.query(tabela, colunas, null, null, null, null, orderBy);
        Log.i("BANCO_DADOS", "Realizou uma busca e retornou [" + c.getCount() + "] registros.");
        return c;
    }

    // Abre conexão com o banco
    private void open(Context ctx) {
        if(!db.isOpen()) {
            // Abre o banco de dados já existente
            db = ctx.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
            Log.i("BANCO_DADOS", "Abriu conexão com o banco.");
        } else {
            Log.i("BANCO_DADOS", "Conexão com o banco já estava aberta.");
        }
    }

    //Retorna a única instância existente dessa classe para qualquer parte do projeto
    public static DataBaseSingleton getInstance(Context ctx){
        if(INSTANCE == null) INSTANCE = new DataBaseSingleton(ctx);
        INSTANCE.open(ctx);   //abre conexão se estiver fechada
        return INSTANCE;
    }

    // Fecha conexão com o banco
    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
            Log.i("BANCO_DADOS", "Fechou conexão com o Banco.");
        }
    }

    public void resetDatabase(Context ctx) {
        Log.i("BANCO_DADOS", "Resetando o Banco de Dados.");
        close(); // Fecha a conexão atual, se aberta
        ctx.deleteDatabase(DB_NAME); // Deleta o arquivo do banco
        INSTANCE = null; // Opcional: força recriação da instância, para reiniciar tudo
    }
}
