package kotleni.duckplay

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.StringRes
import kotleni.duckplay.databinding.DialogLoadingBinding

/**
 * A dialog that shows a loading indicator and a text label.
 * Should be created with [create].
 */
class LoadingDialog private constructor(
    private val dialog: Dialog,
    private val binding: DialogLoadingBinding,
) {
    fun show(@StringRes textRes: Int) {
        binding.label.setText(textRes)
        dialog.show()
    }

    fun hide() {
        dialog.dismiss()
    }

    companion object {
        fun create(context: Context): LoadingDialog {
            val dialog = AlertDialog.Builder(context)
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
            dialog.setView(view)
            val binding = DialogLoadingBinding.bind(view)
            return LoadingDialog(dialog.create(), binding)
        }
    }
}