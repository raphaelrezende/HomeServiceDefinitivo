package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.drawer;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.Calendar;
import java.util.List;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Usuario;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.BaseActivity;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.FormProblemException;

public class EditaUsuarioActivity extends BaseActivity {
    private int ano, mes, dia;
    private final int MIN_PASSWORD = 6;
    private String data = "";
    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_usuario);
        user = recuperaUsuario();
        populaCampos(user);
    }


    private Usuario recuperaUsuario() {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String emailUsuario = sharedPref.getString("email", "HomeService");
        List<Usuario> usuariosList = Select.from(Usuario.class).where(Condition.prop("email").like(emailUsuario)).list();
        return  usuariosList.get(0);
    }

    private void populaCampos (Usuario usuario) {
        setStringFromEdit(R.id.input_usuario_nome_editar, usuario.getNome());
        setStringFromEdit(R.id.input_usuario_email_editar, usuario.getEmail());
        setStringFromEdit(R.id.input_usuario_cpf_editar,usuario.getCpf());
        setStringFromEdit(R.id.input_usuario_cidade_editar,usuario.getCidade());
        setStringFromEdit(R.id.input_senha_edita,usuario.getSenha());
        setStringFromEdit(R.id.input_confirmacao_senha_edita,usuario.getSenha());
        TextView txtData = (TextView) findViewById(R.id.label_usuario_nascimento_editar);
        data = usuario.getNascimento();
        txtData.setText(data);
        RadioButton feminino = (RadioButton) findViewById(R.id.radio_feminino_editar);
        RadioButton masculino = (RadioButton) findViewById(R.id.radio_masculino_editar);
        if(usuario.getSexo().equalsIgnoreCase("feminino")) {
            feminino.setChecked(true);
        }else{
            masculino.setChecked(true);
        }
    }

    public void alteraUsuario(View view) {
        hideKeyboard();
        try{
            checkCampos();
            checkPassword();
            verificaDisponibilidade();
        } catch (FormProblemException e){
            showAlert(e.getMessage());
            return;
        }
        tryEdit(recuperaUsuario().getId());
        showAlert("Alterado com sucesso!");
        finish();
    }
    // Verifica se o email do cadastro está disponivel ou não foi alterado
    private void verificaDisponibilidade() throws FormProblemException {
        if(!user.getEmail().equalsIgnoreCase(getStringFromEdit(R.id.input_usuario_email_editar))) {
            List<Usuario> usuariosList = Select.from(Usuario.class).where(Condition.prop("email").like(
                    getStringFromEdit(R.id.input_usuario_email_editar))).list();
            if (usuariosList.size() > 0) {
                throw new FormProblemException(getString(R.string.error_email_existente));
            }
        }
    }

    private void tryEdit(Long id) {
        String senha = getStringFromEdit(R.id.input_senha_edita);
        String email = getStringFromEdit(R.id.input_usuario_email_editar);
        String nome = getStringFromEdit(R.id.input_usuario_nome_editar);
        String cpf = getStringFromEdit(R.id.input_usuario_cpf_editar);
        String cidade = getStringFromEdit(R.id.input_usuario_cidade_editar);
        RadioGroup rGroup = (RadioGroup) findViewById(R.id.radio_group_editar);
        if(rGroup.getCheckedRadioButtonId() == R.id.radio_feminino_editar) {
            user.setSexo("feminino");
        } else{
            user.setSexo("masculino");
        }
        user.setCidade(cidade);
        user.setCpf(cpf);
        user.setEmail(email);
        user.setNome(nome);
        user.setSenha(senha);
        user.setNascimento(data);
        SugarRecord.save(user);
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", user.getEmail());
        editor.apply();

    }

    private void checkPassword() throws FormProblemException{
        String password = getStringFromEdit(R.id.input_senha_edita);
        if (password.length() < MIN_PASSWORD){
            throw new FormProblemException(getString(R.string.error_password_small));
        }
        String confirmacao = getStringFromEdit(R.id.input_confirmacao_senha_edita);
        if(!password.equalsIgnoreCase(confirmacao)) {
            throw new FormProblemException(getString(R.string.error_password_confirmacao));
        }
    }

    private void checkCampos() throws FormProblemException {
        String senha = getStringFromEdit(R.id.input_senha_edita);
        String email = getStringFromEdit(R.id.input_usuario_email_editar);
        String nome = getStringFromEdit(R.id.input_usuario_nome_editar);
        String cpf = getStringFromEdit(R.id.input_usuario_cpf_editar);
        String cidade = getStringFromEdit(R.id.input_usuario_cidade_editar);
        RadioButton feminino = (RadioButton) findViewById(R.id.radio_feminino_editar);
        RadioButton masculino = (RadioButton) findViewById(R.id.radio_masculino_editar);

        if("".equals(nome)|| "".equals(email)|| "".equals(cpf)|| "".equals(cidade) || "".equals(senha)) {
            throw new FormProblemException(getString(R.string.error_campos));
        }
        if(cpf.length() != 11) {
            throw new FormProblemException(getString(R.string.error_cpf));
        }
        if( "".equals(data)) {
            throw new FormProblemException(getString(R.string.error_data));
        }
        if(!feminino.isChecked() && !masculino.isChecked()) {
            throw new FormProblemException(getString(R.string.error_sexo));
        }
    }

    public void showDatePicker(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"dataPicker");
    }

    @SuppressLint("ValidFragment")
    public class DatePickerFragment  extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar calendario = Calendar.getInstance();
            ano = calendario.get(Calendar.YEAR);
            mes = calendario.get(Calendar.MONTH);
            dia = calendario.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, ano, mes, dia);
        }


        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            ano = year;
            mes = month + 1;
            dia = day;
            atualizarData();
        }

        private void atualizarData() {
            TextView txtData = (TextView) findViewById(R.id.label_usuario_nascimento_editar);
            if(mes >= 10 && dia >= 10){
                String dataNascimento = new StringBuilder().append(dia).append("/").append(mes).append("/").append(ano).append(" ").toString();
                txtData.setText(dataNascimento);
                data = dataNascimento;
            }else {
                if(dia < 10) {
                    String dataNascimento = new StringBuilder().append("0").append(dia).append("/").append(mes).append("/").append(ano).append(" ").toString();
                    txtData.setText(dataNascimento);
                    data = dataNascimento;
                    if(mes < 10) {
                        String nascimento = new StringBuilder().append("0").append(dia).append("/0").append(mes).append("/").append(ano).append(" ").toString();
                        txtData.setText(nascimento);
                        data = nascimento;
                    }
                }else {
                    String dataNascimento = new StringBuilder().append(dia).append("/0").append(mes).append("/").append(ano).append(" ").toString();
                    txtData.setText(dataNascimento);
                    data = dataNascimento;
                }
            }
        }
    }

}
