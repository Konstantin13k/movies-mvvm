package od.konstantin.myapplication.utils.extensions

import android.view.View
import android.view.ViewPropertyAnimator
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.viewbinding.ViewBinding

inline fun <reified BindingT : ViewBinding> Fragment.viewBindings(
    crossinline bind: (View) -> BindingT
) = object : Lazy<BindingT> {
    private var cached: BindingT? = null

    private val observer = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            cached = null
        }
    }

    override val value: BindingT
        get() = cached ?: bind(requireView()).also {
            viewLifecycleOwner.lifecycle.addObserver(observer)
            cached = it
        }

    override fun isInitialized(): Boolean = cached != null
}

@ColorInt
fun Fragment.getColor(colorId: Int): Int {
    return ContextCompat.getColor(requireContext(), colorId)
}

class Animator {

    private var currentAnimationGroup = GroupAnimator()
    private val firstGroup = currentAnimationGroup

    fun animate(
        view: View,
        duration: Long = 0,
        startDelay: Long = 0
    ): ViewPropertyAnimator {
        return currentAnimationGroup.animate(view, duration, startDelay)
    }

    fun then(animation: Animator.() -> Unit) {
        val newAnimationGroup = GroupAnimator(currentAnimationGroup.animationTime)
        currentAnimationGroup.then = newAnimationGroup
        currentAnimationGroup = newAnimationGroup
        this.animation()
    }

    fun cancel() {
        firstGroup.cancel()
    }
}

class GroupAnimator(
    private val defaultDelay: Long = 0,
    var then: GroupAnimator? = null
) {
    private val animationList: MutableList<ViewPropertyAnimator> = arrayListOf()

    val animationTime: Long
        get() {
            val animator = animationList.maxByOrNull { it.duration + it.startDelay }
            return animator?.let { it.duration + it.startDelay } ?: 0L
        }

    fun animate(
        view: View,
        duration: Long = 0,
        startDelay: Long = 0
    ): ViewPropertyAnimator {
        val viewAnimator = view.animate()
            .setDuration(duration)
            .setStartDelay(startDelay + defaultDelay)
        animationList.add(viewAnimator)
        return viewAnimator
    }

    fun cancel() {
        animationList.forEach {
            it.cancel()
        }
        animationList.clear()
        then?.cancel()
        then = null
    }
}

inline fun Fragment.animation(animation: Animator.() -> Unit) {
    val animator = Animator()
    animator.animation()

    val observer = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            animator.cancel()
        }
    }

    viewLifecycleOwner.lifecycle.addObserver(observer)
}