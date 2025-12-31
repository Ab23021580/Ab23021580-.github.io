#!/bin/bash

echo "Verifying Android project structure..."
echo ""

# Check main directories
echo "Checking directories..."
dirs=(
    "app/src/main/java/com/example/noteapp/data"
    "app/src/main/java/com/example/noteapp/ui"
    "app/src/main/java/com/example/noteapp/viewmodel"
    "app/src/main/java/com/example/noteapp/util"
    "app/src/main/res/layout"
    "app/src/main/res/values"
)

for dir in "${dirs[@]}"; do
    if [ -d "$dir" ]; then
        echo "✓ $dir"
    else
        echo "✗ $dir MISSING"
    fi
done

echo ""
echo "Checking key files..."
files=(
    "app/build.gradle.kts"
    "build.gradle.kts"
    "settings.gradle.kts"
    "gradlew"
    "app/src/main/AndroidManifest.xml"
    "app/src/main/java/com/example/noteapp/data/Note.kt"
    "app/src/main/java/com/example/noteapp/data/NoteDao.kt"
    "app/src/main/java/com/example/noteapp/data/NoteDatabase.kt"
    "app/src/main/java/com/example/noteapp/ui/MainActivity.kt"
    "app/src/main/java/com/example/noteapp/ui/AddEditNoteActivity.kt"
    "app/src/main/java/com/example/noteapp/ui/NoteAdapter.kt"
    "app/src/main/java/com/example/noteapp/viewmodel/NoteViewModel.kt"
    "app/src/main/java/com/example/noteapp/util/MemoryCurve.kt"
    "app/src/main/res/layout/activity_main.xml"
    "app/src/main/res/layout/activity_add_edit_note.xml"
    "app/src/main/res/layout/item_note.xml"
    "app/src/main/res/values/strings.xml"
    "app/src/main/res/values/colors.xml"
    "app/src/main/res/values/themes.xml"
)

for file in "${files[@]}"; do
    if [ -f "$file" ]; then
        echo "✓ $file"
    else
        echo "✗ $file MISSING"
    fi
done

echo ""
echo "Project structure verification complete!"
