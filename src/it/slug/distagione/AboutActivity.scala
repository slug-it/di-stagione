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

        val pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        val version = pInfo.versionName;
        findView(TR.about_ver).setText("v" + version)

        val imgCredits = findView(TR.about_img_credits)
        imgCredits.setText(Html.fromHtml(
            "icona by <a href=\"http://mike44nh.deviantart.com/\">mike44nh</a>" +
            " (by-nc-sa 3.0)"))
        imgCredits.setMovementMethod(LinkMovementMethod.getInstance())

        val dataLinks = findView(TR.about_data_links)
        dataLinks.setText(Html.fromHtml(
            "<br />Dati frutta/verdura:<br />" +
            "<a href=\"http://www.paginainizio.com/service/verdurastagione.htm"+
            "\">paginainizio.com</a><br />" +
            "<a href=\"http://www.verduredistagione.it/\">" +
            "verduredistagione.it</a><br />" +
            "<a href=\"http://www.unaproa.com\">unaproa.com</a><br />"
            ))
        dataLinks.setMovementMethod(LinkMovementMethod.getInstance())

        val githubrepo = findView(TR.about_repo)
        githubrepo.setText(Html.fromHtml(
            "github <a href=\"https://github.com/slug-it/di-stagione\">" +
            "repository</a>"
            ))
        githubrepo.setMovementMethod(LinkMovementMethod.getInstance())
    }
}

