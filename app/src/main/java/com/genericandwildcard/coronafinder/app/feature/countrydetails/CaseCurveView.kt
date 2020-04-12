package com.genericandwildcard.coronafinder.app.feature.countrydetails

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style.STROKE
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle


fun Context.spToPx(sp: Float): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp,
        resources.displayMetrics
)

class CaseCurveView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val linesPaint = Paint().apply {
        style = STROKE
    }

    private val labelPaint = TextPaint().apply {
        color = Color.BLACK
        textSize = context.dpToPx(6.SP)
    }

    private fun Context.dpToPx(dp: Float): Float = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
    )

    companion object {
        private val dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    }

    init {
        AndroidThreeTen.init(context.applicationContext) // TODO: Remove
        setPadding(20, 20, 20, 20)
        setCases(listOf(
                // TODO: Remove
                Case(LocalDateTime.now().minusDays(3), 500),
                Case(LocalDateTime.now().minusDays(2), 1000),
                Case(LocalDateTime.now().minusDays(1), 2000),
                Case(LocalDateTime.now(), 4000),
        ))
    }

    private val labelHeight = labelPaint.fontMetrics.let { it.bottom - it.top }

    private var dataset: DataSet? = null

    data class Case(
            val date: LocalDateTime,
            val count: Int
    ) {
        val formattedDate: String = date.format(dateFormat)
    }

    private data class DataSet(
            val maxCases: Case,
            val minCases: Case,
            val firstCase: Case,
            val lastCase: Case,
            val cases: List<Case>
    ) {
//        fun scaledCases(count: Int): List<Case> {
//            if(count >= cases.count()) return cases
//            return listOf(firstCase) + listOf(lastCase)
//        }
    }

    fun setCases(cases: List<Case>) {
        if (cases.isEmpty()) {
            dataset = null
            invalidate()
            return
        }
        dataset = DataSet(
                maxCases = cases.maxBy { it.count }!!,
                minCases = cases.minBy { it.count }!!,
                firstCase = cases.minBy { it.date }!!,
                lastCase = cases.maxBy { it.date }!!,
                cases = cases.sortedBy { it.date }
        )
    }

    data class DataPoint(
            val case: Case,
            val x: Float,
            val y: Float
    )

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (dataset != null) {
            drawContent(canvas, dataset!!)
        } else {
            // TODO: Implement
        }
    }

    private fun drawContent(canvas: Canvas, dataset: DataSet) {
        val countLabelLength = dataset.cases.map { labelPaint.measureText(it.count.toString()) }
        val dateLabelLength = dataset.cases.map { labelPaint.measureText(it.formattedDate) }
        val sideLabelWidth = countLabelLength.max()!! * 1.05f
        val bottomLabelWidth = dateLabelLength.max()!! * 1.05f

        val leftOffset = sideLabelWidth + paddingLeft
        val rightOffset = paddingRight
        val topOffset = paddingTop
        val bottomOffset = bottomLabelWidth + paddingBottom

        val graphWidth = canvas.width - (leftOffset + rightOffset)
        val graphHeight = canvas.height - (topOffset + bottomOffset)

        val dataPoints = dataset.cases.mapIndexed { index, case ->
            DataPoint(
                    case = case,
                    x = leftOffset + getDataPointX(graphWidth, index, dataset.cases.count()),
                    y = topOffset + graphHeight - getDataPointY(graphHeight, case.count, dataset.maxCases.count),
            )
        }
        drawSideLabels(canvas, dataset, bottomLabelWidth, countLabelLength)
        drawBottomLabels(canvas, dataset, sideLabelWidth)
        drawLegendLines(canvas, sideLabelWidth, bottomLabelWidth)
    }

    private fun getDataPointX(space: Float, index: Int, totalCount: Int): Float {
        return (space / totalCount) * index
    }

    private fun getDataPointY(space: Float, count: Int, maxCount: Int, minCount: Int = 0): Float {
        return (space / (maxCount.toFloat() - minCount.toFloat())) * count.toFloat()
    }

    private fun drawSideLabels(canvas: Canvas, dataset: DataSet, bottomLabelHeight: Float, countLabelLength: List<Float>) {
        val canvasHeight = canvas.height - (paddingTop + paddingBottom)
        labelPaint.textAlign = Paint.Align.RIGHT
        val metrics = labelPaint.fontMetricsInt
        val space = canvasHeight - bottomLabelHeight
        dataset.cases.forEachIndexed { index, case ->
//            labelPaint.textAlign = when (index) {
//                0 -> LEFT
//                dataset.cases.size - 1 -> RIGHT
//                else -> CENTER
//            }
            canvas.drawText(
                    case.count.toString(),
                    canvas.leftEdge + countLabelLength[index],
                    canvas.bottomEdge - (bottomLabelHeight + getDataPointY(space, case.count, dataset.maxCases.count) - metrics.height / 2),
                    labelPaint
            )
        }
    }

    private fun drawBottomLabels(canvas: Canvas, dataset: DataSet, sideLabelWidth: Float) {
        val canvasWidth = canvas.width - (paddingLeft + paddingRight)
        val space = (canvasWidth - sideLabelWidth) / (dataset.cases.count() - 1)
        val metrics = labelPaint.fontMetricsInt
        labelPaint.textAlign = Paint.Align.LEFT
        dataset.cases.forEachIndexed { index, case ->
//            labelPaint.textAlign = when (index) {
//                0 -> LEFT
//                dataset.cases.size - 1 -> RIGHT
//                else -> CENTER
//            }
            canvas.save()
            canvas.rotate(
                    -90f,
                    canvas.leftEdge + sideLabelWidth + (space * index),
                    canvas.bottomEdge
            )
            canvas.drawText(
                    case.formattedDate,
                    canvas.leftEdge + sideLabelWidth + (space * index),
                    canvas.bottomEdge + metrics.height / 2,
                    labelPaint
            )
            canvas.restore()
        }
    }

    private val Paint.FontMetricsInt.height get() = -ascent

    private fun drawLegendLines(canvas: Canvas, sideLabelWidth: Float, bottomLabelHeight: Float) {
        // bottom line
        canvas.drawLine(
                canvas.leftEdge + sideLabelWidth,
                canvas.bottomEdge - bottomLabelHeight,
                canvas.rightEdge,
                canvas.bottomEdge - bottomLabelHeight,
                linesPaint
        )

        // side line
        canvas.drawLine(
                canvas.leftEdge + sideLabelWidth,
                canvas.topEdge,
                canvas.leftEdge + sideLabelWidth,
                canvas.bottomEdge - bottomLabelHeight,
                linesPaint
        )
    }

    private val Int.SP get() = context.spToPx(this.toFloat())
    private val Float.SP get() = context.spToPx(this)
    private val Canvas.floatHeight get() = height.toFloat()
    private val Canvas.floatWidth get() = width.toFloat()
    private val Canvas.leftEdge get() = paddingLeft.toFloat()
    private val Canvas.rightEdge get() = (width - paddingRight).toFloat()
    private val Canvas.topEdge get() = paddingTop.toFloat()
    private val Canvas.bottomEdge get() = (height - paddingBottom).toFloat()
}
