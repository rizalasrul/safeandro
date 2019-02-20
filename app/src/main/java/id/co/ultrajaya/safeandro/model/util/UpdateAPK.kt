package id.co.ultrajaya.safeandro.model.util

import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.content.FileProvider
import android.util.Log
import id.co.ultrajaya.safeandro.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class UpdateAPK {
    companion object {
        var mContext: Context? = null
        var urlupdate = "http://b2b.ultrajaya.co.id/msafe2015/msafeupdate.apk"
        var dataupdate = "http://safeweb.ultrajaya.co.id/webapk/apk/#AT1#.apk;#AT1#.apk;ultrajaya.#AT1#"
        var _Activity: Activity? = null

        internal fun update(iContext: Context, iapkName: String) {
            this.mContext = iContext
            _Activity = mContext!! as Activity
            dataupdate = dataupdate.replace("#AT1#", iapkName)

            val pm = mContext!!.getPackageManager()
            val isInstalled = isPackageInstalled("ultrajaya.updateapp", pm)
            if (isInstalled == false) {
                var destination =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/"
                val fileName = "msafeupdate.apk"
                destination += fileName
                val uri = Uri.parse("file://$destination")

                val file = File(destination)
                if (file.exists())
                    file.delete()

                val request = DownloadManager.Request(Uri.parse(urlupdate))
                request.setDescription("HRD Report Android")
                request.setTitle("HRD Report")
                request.setDestinationUri(uri)

                val manager = mContext!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
                val downloadId = manager!!.enqueue(request)

                val finalDestination = destination

                val onComplete = object : BroadcastReceiver() {
                    override fun onReceive(ctxt: Context, intent: Intent) {
                        writeToFile(dataupdate)
                        val install = Intent(Intent.ACTION_VIEW)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            val contentUri = FileProvider.getUriForFile(
                                ctxt,
                                BuildConfig.APPLICATION_ID + ".provider",
                                File(finalDestination)
                            )

                            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            install.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            install.data = contentUri
                            mContext!!.startActivity(install)
                            mContext!!.unregisterReceiver(this)
                            _Activity!!.finish()
                        } else {
                            install.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            install.setDataAndType(uri, manager.getMimeTypeForDownloadedFile(downloadId))
                            mContext!!.startActivity(install)
                            mContext!!.unregisterReceiver(this)
                            _Activity!!.finish()
                        }

                    }
                }
                mContext!!.registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
            } else {
                panggilprogramupdate()
                _Activity!!.finish()
            }
        }

        private fun writeToFile(data: String) {
            try {
                val sdCard = Environment.getExternalStorageDirectory()
                val dir = File(sdCard.absolutePath + "/safeupdate")
                dir.mkdirs()
                val file = File(dir, "config.txt")
                val stream = FileOutputStream(file)
                try {
                    stream.write(data.toByteArray())
                } finally {
                    stream.close()
                }

            } catch (e: IOException) {
                Log.e("Exception", "File write failed: " + e.toString())
            }

        }

        private fun isPackageInstalled(packagename: String, packageManager: PackageManager): Boolean {
            try {
                packageManager.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES)
                return true
            } catch (e: PackageManager.NameNotFoundException) {
                return false
            }

        }

        internal fun panggilprogramupdate() {

            val launchIntent = mContext!!.getPackageManager().getLaunchIntentForPackage("ultrajaya.updateapp")
            if (launchIntent != null) {

                launchIntent.setAction(Intent.ACTION_SEND)
                launchIntent.putExtra(Intent.EXTRA_TEXT, dataupdate)
                launchIntent.setType("text/plain")
                mContext!!.startActivity(launchIntent)
                _Activity!!.finish()
            }
        }

    }
}