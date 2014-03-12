package it.slug.distagione

import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.View.MeasureSpec
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import android.content.Context


class TabBar(ctxt: Context, as: AttributeSet)
    extends HorizontalScrollView(ctxt, as)
{
    /* ------------ ctor ---------------------------------------------------- */
    // view variables
    private var mLayout = new LinearLayout(ctxt)

    // support variables
    private var mMaxTabWidth = -1           // max width for a single tab
    private var mSelectedIndex = -1         // index of selected tab
    private var mAnimation: Runnable = null // handler to the current animation
    private var entries: Map[CharSequence, TextView] = Map()  // entries map

    // initializations
    setHorizontalScrollBarEnabled(false)
    addView(
        mLayout,
        new ViewGroup.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.MATCH_PARENT
            )
        )
    /* ---------------------------------------------------------------------- */

    /* ------------ public members ------------------------------------------ */
    def addTab(text: CharSequence, style: Int): TabBarItem = {
        val newItem = new TabBarItem(getContext(), style)
        newItem.setFocusable(true)
        newItem.setText(text)
        entries += (text -> newItem)
        mLayout.addView(
            newItem,
            new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1)
            )
        newItem
    }

    def selectedIndex = mSelectedIndex

    def setCurrentItem(index: Int): Unit = {
        // preliminary checks
        if (index == mSelectedIndex) return
        val itemsCount = mLayout.getChildCount()
        if (index < 0 || index >= itemsCount) return
        mSelectedIndex = index
        val selChild = mLayout.getChildAt(index)
        var i = 0
        for (i <- 0 until itemsCount) {
            if (i != index) mLayout.getChildAt(i).setSelected(false)
        }
        selChild.setSelected(true)
        createAnimation(index)
        runAnimation()
    }

    def item(name: CharSequence): Option[TextView] = entries.get(name)
    /* ---------------------------------------------------------------------- */

    /* ------------ events overriding --------------------------------------- */
    override def onMeasure(widthSpec: Int, heightSpec: Int): Unit = {
        // retrieve width infos
        val widthMode = MeasureSpec.getMode(widthSpec)
        val widthSize = MeasureSpec.getSize(widthSpec)

        // retrieve height infos
        val heightMode = MeasureSpec.getMode(heightSpec)
        val heightSize = MeasureSpec.getSize(heightSpec)

        val childCount = mLayout.getChildCount()
        val expansionLocked = (widthMode == MeasureSpec.EXACTLY)

        setFillViewport(expansionLocked)

        if (childCount > 1 &&
            (widthMode == MeasureSpec.EXACTLY ||
                widthMode == MeasureSpec.AT_MOST))
        {
            if (childCount > 2) {
                mMaxTabWidth = (widthSize * 0.4f).toInt
            } else {
                mMaxTabWidth = widthSize / 2
            }
        } else {
            mMaxTabWidth = -1
        }

        var oldWidth = getMeasuredWidth()
        super.onMeasure(widthSpec, heightSpec)
        var newWidth = getMeasuredWidth()
    }

    override def onAttachedToWindow(): Unit = {
        super.onAttachedToWindow()
        runAnimation()
    }

    override def onDetachedFromWindow(): Unit = {
        super.onDetachedFromWindow()
        cancelAnimation()
    }
    /* ---------------------------------------------------------------------- */

    /* ------------ private members ----------------------------------------- */
    private def cancelAnimation(): Unit = {
        if (mAnimation != null) removeCallbacks(mAnimation)
    }

    private def runAnimation(): Unit = {
        if (mAnimation != null) post(mAnimation)
    }

    private def createAnimation(toIndex: Int): Unit = {
        cancelAnimation()
        val targetItem = mLayout.getChildAt(toIndex)

        mAnimation = new Runnable {
            def run {
                val targetLeft = (getWidth() - targetItem.getWidth()) / 2
                val scrollOffset = targetItem.getLeft() - targetLeft
                smoothScrollTo(scrollOffset, 0)
                mAnimation = null   // gc here
            }
        }
    }
    /* ---------------------------------------------------------------------- */
}

class TabBarItem(ctxt: Context, style: Int)
    extends TextView(ctxt, null, style)
{

}
