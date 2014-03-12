package it.slug.distagione;

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import android.text.method.LinkMovementMethod
import android.text.Html


class AboutActivity extends Activity with TypedViewHolder {

    override def onCreate(savedInstanceState: Bundle) : Unit = {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)

        findView(TR.about_header).setText("Di Stagione")

        val pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        val version = pInfo.versionName;
        findView(TR.about_ver).setText("v" + version)

        val imgCredits = findView(TR.about_img_credits)
        imgCredits.setText(Html.fromHtml(
            "icon by <a href=\"http://mike44nh.deviantart.com/\">mike44nh</a>" +
            " (by-nc-sa 3.0)"))
        imgCredits.setMovementMethod(LinkMovementMethod.getInstance())

        val githubrepo = findView(TR.about_repo)
        githubrepo.setText(Html.fromHtml(
            "github <a href=\"https://github.com/slug-it/di-stagione\">" +
            "app repo</a>"
            ))
        githubrepo.setMovementMethod(LinkMovementMethod.getInstance())
    }
}
