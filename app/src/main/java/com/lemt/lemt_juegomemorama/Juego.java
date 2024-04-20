package com.lemt.lemt_juegomemorama;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class Juego extends Activity {
    ImageView curView = null;
    Button btnReinicio,btnVolver;
    TextView txtPuntuacion,txtTimer,txtMovimientos;
    Handler handler;
    CountDownTimer contador;
    int puntuacion;
    int aciertos;
    private int contarPar = 0;
    int[] drawable;
    int[] pos = {0,1,2,3,4,5,6,7,0,1,2,3,4,5,6,7};
    int currentPos = -1;
    boolean activo=false;
    boolean bandera=false;
    int movimientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.juego);
        handler = new Handler();
        Iniciar_Partida();
    }
    public void iniciar_variables(){
        drawable = new int[] {
                R.drawable.yoshi,
                R.drawable.boo,
                R.drawable.mario,
                R.drawable.luigi,
                R.drawable.cielo,
                R.drawable.peach,
                R.drawable.ink,
                R.drawable.bowser
        };
        contarPar = 0;
        currentPos = -1;
        movimientos=0;
    }
    public void cargarBotones(){

        btnReinicio=findViewById(R.id.btnReiniciar);
        btnReinicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Iniciar_Partida();

            }
        });
        btnVolver=findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               contador.cancel();
               finish();
            }
        });
    }
    private void cargarTexto(){
        txtPuntuacion = findViewById(R.id.txtPuntuacion);
        txtMovimientos = findViewById(R.id.txtmovimientos);
        puntuacion = 0;
        aciertos = 0;
        txtPuntuacion.setText("Puntuacion: " + puntuacion);
        txtMovimientos.setText("Movimientos: "+movimientos);
    }
    private static void Randomizar(int[] array) {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i)
            {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }
   public void Temporizador(){
        if (!activo) {
            activo=true;
            txtTimer=findViewById(R.id.txtTimer);
            contador=new CountDownTimer(120000,1000) {
                @Override
                public void onTick(long l) {
                    long tiempo = l/1000;
                    int minutos = (int) (tiempo/60);
                    long segundo = tiempo%60;
                    String mintxt=String.format("%02d",minutos);
                    String segtxt=String.format("%02d",segundo);
                    txtTimer.setText(""+mintxt+":"+segtxt);
                }
                @Override
                public void onFinish() {
                    mostrarDialogoPersonalizado("Se acabo el tiempo \n Movimientos : "+movimientos+"\n Puntuacion: "+puntuacion,bandera);
                    txtTimer.setText("00:00");
                }
            }.start();
        }
            else{
            contador.cancel();
            activo=false;
            Temporizador();
        }
   }


    public void Iniciar_Partida(){
        iniciar_variables();
        Randomizar(pos);
        ImageAdapter imageAdapter = new ImageAdapter(this,drawable,pos);
        GridView gridView = (GridView)findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);
        cargarBotones();
        cargarTexto();
        Temporizador();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (currentPos < 0 ) {
                    currentPos = position;
                    curView = (ImageView) view;
                    ((ImageView) view).setImageResource(drawable[pos[position]]);
                }
                else {
                    if (currentPos == position) {
                        ((ImageView) view).setImageResource(R.drawable.fondo);

                    } else if (pos[currentPos] != pos[position]) {
                        ((ImageView) view).setImageResource(drawable[pos[position]]);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                curView.setImageResource(R.drawable.fondo);
                                ((ImageView) view).setImageResource(R.drawable.fondo);
                            }
                        }, 150);

                        Toast.makeText(Juego.this, "No Coinciden!", Toast.LENGTH_SHORT).show();
                        if (puntuacion>0){
                            puntuacion--;
                        }
                        movimientos++;
                        txtPuntuacion.setText("Puntuación: " + puntuacion);
                        txtMovimientos.setText("Movimentos: "+movimientos);
                    } else {
                        ((ImageView) view).setImageResource(drawable[pos[position]]);
                        contarPar++;
                        aciertos++;
                        puntuacion++;
                        movimientos++;
                        txtPuntuacion.setText("Puntuación: " + puntuacion);
                        txtMovimientos.setText("Movimentos: "+movimientos);
                        if (contarPar == 8) {
                            mostrarDialogoPersonalizado("!!Has Ganado!!\nmovimientos :"+movimientos+"\n puntuacion: " +puntuacion,!bandera);
                        }
                    }
                    currentPos = -1;
                }
            }
        });
    }
    private void mostrarDialogoPersonalizado(String txt,boolean bandera){
        contador.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(Juego.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.mensage,null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        TextView tvtMensaje = view.findViewById(R.id.text_dialog);
        tvtMensaje.setText(txt);
        ImageView imageBandera=view.findViewById(R.id.imgIcon);
        Button btnReintentar = view.findViewById(R.id.btnReintentar);
        Button btnClose= view.findViewById(R.id.btnCancel);
        if (bandera){
            imageBandera.setImageResource(R.drawable.star);
            imageBandera.setBackgroundColor(getResources().getColor(R.color.orange));
            btnReintentar.setBackground(getResources().getDrawable(R.drawable.boton_win));
            btnClose.setBackground(getResources().getDrawable(R.drawable.borde_win));
        }
        else{
            imageBandera.setImageResource(R.drawable.fail2);
            imageBandera.setBackgroundColor(getResources().getColor(R.color.sky));
            btnReintentar.setBackground(getResources().getDrawable(R.drawable.boton_lose));
            btnClose.setBackground(getResources().getDrawable(R.drawable.borde_lose));
        }

        btnReintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Iniciar_Partida();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
    }
}
