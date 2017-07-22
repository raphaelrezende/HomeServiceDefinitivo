package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Usuario;
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
        SugarContext.init(this);
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


        try {
            tryLogin(password,email);
        } catch (FormProblemException e) {
            showAlert(e.getMessage());
        }
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
            throw new FormProblemException(getString(R.string.error_email_empty));
        }
    }

    private void tryLogin(String password, String email) throws FormProblemException {
        // Implementar a verificação de credenciais
        List<Usuario> usuariosList = Select.from(Usuario.class).where(Condition.prop("email").like(email)).list();
        dismissDialog();
        if(usuariosList.size() > 0) {
            Usuario usuario =  usuariosList.get(0);
            if(usuario.getSenha().equalsIgnoreCase(password)) {
                final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("email", usuario.getEmail());
                editor.putString("nome", usuario.getNome());
                editor.apply();
                goToCategorias();
            }else {
                throw new FormProblemException(getString(R.string.error_fail_login));
            }
        }else {
            throw new FormProblemException(getString(R.string.error_fail_login));
        }
    }

    private void goToCategorias() {
        Intent intent = new Intent(this, ListaCategoriasActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dismissDialog();
        setStringFromEdit(R.id.password,"");
    }


    public void goToCadastrar(View v) {
        Intent intent = new Intent(this, CadastroUsuarioActivity.class);
        startActivity(intent);

    }
}

