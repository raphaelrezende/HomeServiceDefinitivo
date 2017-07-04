package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.BaseActivity;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.FormProblemException;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.categorias.ListaCategoriasActivity;

public class LoginActivity extends BaseActivity {

    private final int MIN_PASSWORD = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View v){

        hideKeyboard();

        try{
            checkEmail();
            checkPassword();
        } catch (FormProblemException e){
            showAlert(e.getMessage());
            return;
        }

        String password = getStringFromEdit(R.id.password);
        String email = getStringFromEdit(R.id.username);

        showDialogWithMessage(getString(R.string.load_login));


        tryLogin(password,email);
    }


    private void checkPassword() throws FormProblemException{
        String password = getStringFromEdit(R.id.password);
        if("".equals(password)){
            throw new FormProblemException(getString(R.string.error_password_empty));
        }

        if (password.length() < MIN_PASSWORD){
            throw new FormProblemException(getString(R.string.error_password_small));
        }
    }

    private void checkEmail() throws FormProblemException{
        String email = getStringFromEdit(R.id.username);
        if("".equals(email)){
            throw new FormProblemException(getString(R.string.error_password_empty));
        }
    }

    private void tryLogin(String password, String email) {
        // Implementar a verificação de credenciais
        goToCategorias();
    }

    private void goToCategorias() {
        Intent intent = new Intent(this, ListaCategoriasActivity.class);
        startActivity(intent);
        //finish();
    }
//
//    public void goToCadastrar(View v) {
//        Intent intent = new Intent(this, CadastroActivity.class);
//        startActivity(intent);
//        //finish();
//
//    }
}

