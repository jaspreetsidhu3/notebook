package com.example.notebook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class SaveDialogFragment extends AppCompatDialogFragment {
    String data;
    private EditText editfilename;

    public SaveDialogFragment(String data) {
        this.data = data;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.savedialog, null);
        editfilename = view.findViewById(R.id.filename);
        builder.setView(view)
                .setTitle("Save")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Moved back", Toast.LENGTH_SHORT).show();
                    }
                }).setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FileOutputStream fos = null;
                try {
                    String filename = editfilename.getText().toString();
                    fos = getContext().openFileOutput(filename + ".txt", getContext().MODE_PRIVATE);
                    fos.write(data.getBytes());

                    Toast.makeText(getContext(), "Saved to " + getContext().getFilesDir(), Toast.LENGTH_SHORT).show();

                } catch (
                        FileNotFoundException e) {
                    e.printStackTrace();
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
                try {
                    assert fos != null;
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        })
        ;

        return builder.show();

    }
}
