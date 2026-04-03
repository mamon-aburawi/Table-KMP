
# 📊 Table KMP

![Kotlin](https://img.shields.io/badge/kotlin-multiplatform-blue.svg?logo=kotlin)
![Compose](https://img.shields.io/badge/Compose-Multiplatform-green.svg)
![Android](https://img.shields.io/badge/Android-3DDC84.svg?logo=android&logoColor=white)
![iOS](https://img.shields.io/badge/iOS-000000.svg?logo=apple&logoColor=white)
![Desktop](https://img.shields.io/badge/Desktop-4A4A55.svg)
![Web](https://img.shields.io/badge/Web-654FF0.svg?logo=webassembly&logoColor=white)
![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Version](https://img.shields.io/badge/version-1.0.1-orange.svg)

**TableKMP** is a highly customizable, deeply interactive, and buttery-smooth data grid / table library built natively for **Compose Multiplatform**. 

Whether you need a simple list, a dense enterprise financial grid, or a fully interactive Kanban-style task board with drag-and-drop, TableKMP adapts perfectly across Desktop, Web, iOS, and Android.

---

## ✨ Key Features

* **📱 100% Multiplatform:** Write once, run perfectly on Android, iOS, Desktop (JVM), and Web (Wasm/JS).
* **🖐️ Physics-Based Drag & Drop:** Fluid, spring-animated row reordering with center-point collision and scroll-safe mechanics (items stay glued to the pointer even when using a mouse wheel).
* **🎨 Ultimate Customization:** Inject *any* Composable into your cells. Support for avatars, progress bars, chips, and complex nested layouts.
* **🌗 Theming:** Granular control over every color. Automatically cascades text/icon colors based on row selection.
* **📏 Responsive Design:** Support for column weights alongside `minWidth`. Adapts beautifully to mobile screens while expanding gracefully on desktop monitors.
* **✅ Built-in Selection Engine:** Native support for row selection with customizable checkboxes and sticky selection states.
* **📜 Smart Scrolling Tools:** Custom draggable vertical and horizontal scrollbars for intense data grids.
* **⏳ Lifecycle States:** Built-in support for `isLoading` views, `onEmpty` states, and custom `footerContent`.

---

## 🌐 Platform Support

TableKMP is designed to feature-match across all Compose Multiplatform targets.

| Feature | 🤖 Android | 🍏 iOS | 💻 Desktop (JVM) | 🌐 Web (Wasm/JS) |
| :--- | :---: | :---: | :---: | :---: |
| **Core Rendering** | ✅ | ✅ | ✅ | ✅ |
| **Custom Cell Content**| ✅ | ✅ | ✅ | ✅ |
| **Drag & Drop** | ✅ | ✅ | ✅ | ✅ |
| **Row Selection** | ✅ | ✅ | ✅ | ✅ |
| **Horizontal Scroll** | ✅ | ✅ | ✅ | ✅ |
| **Hover Effects** | ➖ | ➖ | ✅ | ✅ |
| **Ripple Control** | ✅ | ✅ | ✅ | ✅ |



## 📸 Screen Shoot

Check out how TableKMP looks and feels across different scenarios.

### 🖥️ Desktop / Web Interfaces

**1. Enterprise Financial Grid** *Dense data, horizontal scrolling, sticky selection, striped rows, and rich avatars.* <br>
<img width="1820" height="628" alt="Enterprise Financial Grid" src="https://github.com/user-attachments/assets/9232cc56-e1cb-49bc-9bb0-2abe67031523" />

**2. Modern Admin Dashboard** *Clean user management dashboard with dark headers, pill badges, and action icons.* <br>
<img width="1826" height="422" alt="Modern Admin Dashboard" src="https://github.com/user-attachments/assets/8d74fac8-d3ca-4ee6-a780-d08b097c50d0" />

**3. Dark Mode Analytics** *Fully inverted theme, borderless rows, hover highlighting, and status indicators.* <br>
<img width="1827" height="687" alt="Mode Analysis" src="https://github.com/user-attachments/assets/dc0921b0-5a70-44e4-bcc6-5619b3856ce2" />

**4. Clean Users Directory** *Spacious rows, stacked text, custom pill coloring, and interactive action buttons.* <br>
<img width="1826" height="622" alt="Clean User Directory" src="https://github.com/user-attachments/assets/895eda05-9dfc-4035-bc77-58b38cf51092" />

**5. Interactive Task Board** *Drag & Drop reordering enabled. Features row spacing, card elevations, and custom badges.* <br>
<img width="1847" height="721" alt="Interactive Task Board" src="https://github.com/user-attachments/assets/6a485f4f-8abb-4394-85f6-ad54d265ebf1" />

---

## 🎥 Video Tutorials & Previews

<div align="center">

**TableKMP in Action: Drag & Drop + Auto-Scrolling** <br>
<video src="https://github.com/user-attachments/assets/a7730ca1-9091-4014-80f9-47a18774b7cb" width="850" controls style="max-width: 100%; border: 1px solid #ddd; border-radius: 8px; margin-bottom: 20px;"></video>

<br>

**TableKMP in Action: Smooth Swapping & Selection** <br>
<video src="https://github.com/user-attachments/assets/0e27e9ac-1557-4bd5-a952-53ead8aebfa6" width="850" controls style="max-width: 100%; border: 1px solid #ddd; border-radius: 8px;"></video>

</div>

---

## 🚀 Installation

Add the dependency to your `build.gradle.kts` in the `commonMain` source set:

```kotlin
sourceSets {
    commonMain.dependencies {
        implementation("io.github.mamon-aburawi:table-kmp:{last_version}")

    }
}
````

-----

## 💻 How to Use

TableKMP is designed to be incredibly intuitive. Define your columns, pass your data, and you're done.

### 1\. The Basics

```kotlin
data class User(val name: String, val role: String, val status: String)

val myUsers = listOf(
    User("Alice", "Admin", "Active"),
    User("Bob", "Contributor", "Pending")
)

@Composable
fun SimpleTable() {
    val columns = listOf(
        TableColumn<User>("Name", weight = 2f) { user -> Text(user.name, fontWeight = FontWeight.Bold) },
        TableColumn<User>("Role", weight = 1f) { user -> Text(user.role) },
        TableColumn<User>("Status", weight = 1f) { user  -> Text(user.status) }
    )

    Table(
        items = myUsers,
        columns = columns
    )
}
```

### 2\. Advanced: Drag & Drop, Selection, and Theming

By utilizing `TableConfig` and `TableColors`, you can transform the table into a highly interactive dashboard.

```kotlin
var selectedUsers by remember { mutableStateOf(emptyList<User>()) }
var tableData by remember { mutableStateOf(myUsers) }

Table(
    items = tableData,
    columns = columns,
    onSelectionChanged = { selectedUsers = it },
    onItemsReordered = { tableData = it }, // Triggers when Drag & Drop completes
    config = TableConfig(
        dragDropEnabled = true,          // Enables physics-based reordering
        selectionEnabled = true,         // Shows checkboxes
        stripedEnabled = true,           // Alternating row colors
        rowShape = RoundedCornerShape(8.dp), // Rounded rows
        borderWidth = 1.dp               // Outlined borders
    ),
    colors = TableColors(
        headerContainerColor = Color(0xFF0F172A),
        headerContentColor = Color.White,
        selectedRowContainerColor = Color(0xFFEFF6FF),
        selectedRowContentColor = Color(0xFF3B82F6) // Automatically tints text/icons!
    )
)
```

-----


## ⚙️ API Reference

### 1\. `TableColumn<T>`

Configuration for individual columns inside the table.

| Parameter | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `name` | `String` | **Required** | The title or label of the column, displayed in the table header. |
| `minWidth` | `Dp` | `120.dp` | The absolute minimum width this column is allowed to shrink to. Crucial for horizontal scrolling calculations. |
| `weight` | `Float` | `1f` | The flexible width proportion relative to other columns, similar to Compose's `Modifier.weight()`. |
| `headerAlignment` | `Alignment.Horizontal` | `Start` | The horizontal alignment (`Start`, `CenterHorizontally`, `End`) of the text inside the column's header. |
| `contentAlignment` | `Alignment.Horizontal` | `Start` | The horizontal alignment (`Start`, `CenterHorizontally`, `End`) of the items inside the column's body cells. |
| `content` | `@Composable` | **Required** | A composable lambda that dictates how the cell's UI is rendered. Provides the current row `item` and a `isSelected` boolean. |

### 2\. Main `Table<T>` Composable

The primary entry point for rendering your data grid.

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `items` | `List<T>` | The list of data items to be displayed in the table. |
| `columns` | `List<TableColumn<T>>` | The definitions for each column, including sizing and content rendering logic. |
| `modifier` | `Modifier` | Standard Compose modifier applied to the root container. |
| `isLoading` | `Boolean` | Determines whether the table should hide its content and display the loading state. |
| `config` | `TableConfig` | Defines the structural and behavioral properties of the table (e.g., selection, dragging, shapes). |
| `colors` | `TableColors` | Defines the exact color palette for headers, rows, and tools. |
| `onItemClicked` | `(T) -> Unit` | Callback invoked when a user clicks on a table row. Provides the clicked item. |
| `onSelectionChanged` | `(List<T>) -> Unit` | Callback invoked when row selection changes. Provides the updated list of selected items. |
| `onItemsReordered` | `(List<T>) -> Unit` | Callback invoked after a user finishes dragging and dropping a row to a new position. |
| `onLoad` | `@Composable () -> Unit` | A composable lambda to render a custom loading UI when `isLoading` is true. |
| `onEmpty` | `@Composable () -> Unit` | A composable lambda to render a custom empty state when `items` is empty and `isLoading` is false. |
| `footerContent` | `@Composable (() -> Unit)?` | An optional composable lambda to render sticky or static content at the bottom of the table layout. |

### 3\. `TableConfig`

Controls the physical dimensions, toggles, and interaction behaviors of the table.

| Parameter | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `stripedEnabled` | `Boolean` | `false` | Applies alternating background colors to rows for better readability. |
| `showHorizontalScrollTool` | `Boolean` | `false` | Displays a custom draggable tool for horizontal scrolling when the table exceeds screen width. |
| `showVerticalScrollTool` | `Boolean` | `false` | Displays a custom draggable tool for vertical scrolling. |
| `selectionEnabled` | `Boolean` | `false` | Renders a leading column with checkboxes allowing users to select multiple rows. |
| `dragDropEnabled` | `Boolean` | `false` | Enables long-press drag-and-drop gestures for reordering rows. |
| `hoverEnabled` | `Boolean` | `false` | Applies a visual hover effect when a pointer (like a mouse) rests over a row. |
| `highlightClickedRow` | `Boolean` | `false` | Visually highlights a row indefinitely after it has been clicked. |
| `rippleEffectEnabled` | `Boolean` | `false` | Displays the standard Material design ripple animation when a row is clicked. |
| `borderWidth` | `Dp` | `0.5.dp` | The thickness of the borders separating rows and columns. Set to `0.dp` to remove. |
| `rowHeight` | `Dp` | `60.dp` | The fixed height for standard data rows. |
| `headerHeight` | `Dp` | `60.dp` | The fixed height for the top header row. |
| `rowShape` | `Shape` | `RectangleShape` | The Compose `Shape` applied to data rows. Useful when adding row spacing. |
| `headerShape` | `Shape` | `RectangleShape` | The Compose `Shape` applied to the header row. |
| `rowSpacing` | `Dp` | `0.dp` | The vertical gap distance between individual rows. |
| `rowElevation` | `Dp` | `0.dp` | The shadow elevation applied to data rows. Often used with `rowSpacing`. |
| `contentPadding` | `Dp` | `0.dp` | Outer padding applied around the entire table content. |

### 4\. `TableColors`

Granular control over the visual theme. Designed to work flawlessly with both Light and Dark modes.

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `headerContainerColor` | `Color` | The background color of the table header row. |
| `headerContentColor` | `Color` | The color of the text and icons within the table header. |
| `selectedRowContainerColor` | `Color` | The background color of a row when it is actively selected. |
| `rowContainerColor` | `Color` | The default background color of unselected rows. |
| `selectedRowContentColor` | `Color` | The color of the text and icons within a selected row. |
| `rowContentColor` | `Color` | The default color of the text and icons within unselected rows. |
| `hoverColor` | `Color` | The background color overlay applied when a pointer hovers over a row. |
| `rowBorderColor` | `Color` | The color of the borders separating rows or framing the table. |
| `trackColor` | `Color` | The color of the scrollbar track when scrolling is active. |
| `rowShadowColor` | `Color` | The color of the drop shadow applied to rows (e.g., during drag-and-drop). |
| `headerCheckboxCheckedColor` | `Color` | Background color of the header's select-all checkbox when checked. |
| `headerCheckboxUncheckedColor` | `Color` | Border color of the header's select-all checkbox when unchecked. |
| `headerCheckboxCheckmarkColor` | `Color` | Color of the checkmark icon inside the header's checkbox. |
| `rowCheckboxCheckedColor` | `Color` | Background color of a row's individual checkbox when checked. |
| `rowCheckboxUncheckedColor` | `Color` | Border color of a row's individual checkbox when unchecked. |
| `rowCheckboxCheckmarkColor` | `Color` | Color of the checkmark icon inside a row's checkbox. |

-----

## 🤝 Contributing

Contributions, issues, and feature requests are welcome.

## 📄 License

This project is licensed under the MIT License.

```
```
