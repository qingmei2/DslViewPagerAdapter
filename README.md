# DslViewPagerAdapter

A simple and light FragmentViewPagerAdapter.

## Usage

### 1.usage of dataBinding.

```xml
<android.support.v4.view.ViewPager
        android:id="@+id/viewPager"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:onViewPagerPageChanged="@{ (index) -> activity.onPageSelectChanged(index) }"
        app:viewPagerAdapter="@{ activity.adapter }"
        app:viewPagerDefaultItem="@{ 2 }"
        app:viewPagerPageLimit="@{ 1 }" />
```

and configrate it in your activity:

```kotlin
class MainActivity : AppCompatActivity() {

    private var fragments: List<Fragment> =
            listOf(AFragment(), BFragment(), CFragment())

    val adapter: DslFragmentPagerAdapter = DslFragmentPagerAdapter.build(
            fragmentManager = supportFragmentManager,
            fragmentsProvider = { fragments },
            recycleFragment = { old, _ ->
                when (old) {
                    is AFragment -> true
                    else -> false
                }
            }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        // binding it.
    }

    fun onPageSelectChanged(index: Int) {
        Toast.makeText(this, "onPageChanged = $index", Toast.LENGTH_SHORT).show()
    }
}
```

### 2.usage exclude dataBinding.

```kotlin
viewPager.buildAdapter(
        fragmentManager = supportFragmentManager,
        currentItem = 0,
        offscreenPageLimit = 1,
        fragments = { fragments },
        recycle = { old, _ ->
            when (old) {
                is AFragment -> true
                else -> false
            }
        }
)
```

## License

    The DslViewPagerAdapter: Apache License

    Copyright (c) 2018 qingmei2

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
