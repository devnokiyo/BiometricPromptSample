package com.devnokiyo.biometricpromptsample

import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var cancelSignal: CancellationSignal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cancelSignal = CancellationSignal()
        val builder = BiometricPrompt.Builder(this)
        builder.setTitle("生体認証します")
        builder.setNegativeButton("キャンセル", mainExecutor, DialogInterface.OnClickListener { dialogInterface, i ->
            cancelSignal.cancel()
        })
        builder.build().authenticate(cancelSignal, mainExecutor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                when (errorCode) {
                    BiometricPrompt.BIOMETRIC_ERROR_NO_BIOMETRICS ->
                        Toast.makeText(this@MainActivity, "非対応です。", Toast.LENGTH_SHORT).show()
                    else ->
                        Toast.makeText(this@MainActivity, "その他のエラーです。", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
                throw RuntimeException("Stub!")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                Toast.makeText(this@MainActivity, "認証成功です。", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                Toast.makeText(this@MainActivity, "認証失敗です。", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
