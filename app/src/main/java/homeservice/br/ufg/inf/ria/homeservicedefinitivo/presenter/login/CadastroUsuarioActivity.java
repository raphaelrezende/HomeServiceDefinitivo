package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.login;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.orm.SugarContext;
import com.orm.SugarRecord;

import java.util.Calendar;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Usuario;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.BaseActivity;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.FormProblemException;

public class CadastroUsuarioActivity extends BaseActivity {
    private int ano, mes, dia;
    private final int MIN_PASSWORD = 6;
    private String data = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        SugarContext.init(this);
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"datePicker");
    }
    public void cadastraUsuario (View view) {
        hideKeyboard();

        try{
            checkCampos();
            checkPassword();
        } catch (FormProblemException e){
            showAlert(e.getMessage());
            return;
        }
        showAlert("Cadastrado com sucesso");
        realizaCadastro();
        finish();

    }

    private void realizaCadastro() {
        String senha = getStringFromEdit(R.id.input_usuario_senha);
        String email = getStringFromEdit(R.id.input_usuario_email);
        String nome = getStringFromEdit(R.id.input_usuario_nome);
        String cpf = getStringFromEdit(R.id.input_usuario_cpf);
        String cidade = getStringFromEdit(R.id.input_usuario_cidade);
        RadioGroup rGroup = (RadioGroup) findViewById(R.id.radio_group);
        Usuario usuario = new Usuario();
        if(rGroup.getCheckedRadioButtonId() == R.id.radio_feminino) {
            usuario.setSexo('f');
        } else{
            usuario.setSexo('m');
        }
        usuario.setCidade(cidade);
        usuario.setCpf(cpf);
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setSenha(senha);
        usuario.setNascimento(data);
        SugarRecord.save(usuario);
    }

    private void checkPassword() throws FormProblemException{
        String password = getStringFromEdit(R.id.input_usuario_senha);
        if (password.length() < MIN_PASSWORD){
            throw new FormProblemException(getString(R.string.error_password_small));
        }
        String confirmacao = getStringFromEdit(R.id.input_usuario_confirmacao_senha);
        if(!password.equalsIgnoreCase(confirmacao)) {
            throw new FormProblemException(getString(R.string.error_password_confirmacao));
        }
    }

    private void checkCampos() throws FormProblemException {
        String password = getStringFromEdit(R.id.input_usuario_senha);
        String email = getStringFromEdit(R.id.input_usuario_email);
        String nome = getStringFromEdit(R.id.input_usuario_nome);
        String cpf = getStringFromEdit(R.id.input_usuario_cpf);
        String cidade = getStringFromEdit(R.id.input_usuario_cidade);
        RadioButton feminino = (RadioButton) findViewById(R.id.radio_feminino);
        RadioButton masculino = (RadioButton) findViewById(R.id.radio_masculino);

        if("".equals(nome)|| "".equals(email)|| "".equals(cpf)|| "".equals(cidade) || "".equals(password)) {
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
            TextView txtData = (TextView) findViewById(R.id.label_usuario_nascimento);
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
