#!/bin/bash

# Universal Java Exercise Test Script
# Usage: ./test.sh <exercise-name>
# Automatically finds and tests exercises across all groups

if [ $# -eq 0 ]; then
    echo "Usage: $0 <exercise-name>"
    echo ""
    echo "Available exercises:"
    echo ""
    echo "📁 Group 1:"
    echo "  - MultiplicationTable"
    echo "  - TimeTracker" 
    echo "  - AgeFinder"
    echo "  - TodoList"
    echo "  - DayOfWeek"
    echo "  - MonthlyPeriod"
    echo ""
    echo "📁 Group 2:"
    echo "  - HTMLValidator"
    echo "  - Flexisort"
    echo "  - NextPrime"
    echo "  - AlmostPalindrome"
    echo "  - BreakdownURL"
    echo "  - ConfigProtector"
    echo "  - FactorialMaster"
    echo ""
    echo "📁 Group 3:"
    echo "  - CircularLinkedList"
    echo "  - SingletonBlueprint"
    echo "  - DoubleLinkedList"
    echo "  - SingleLinkedList"
    echo "  - FactoryBlueprint"
    echo "  - BuilderBlueprint"
    echo ""
    echo "📁 Group 4:"
    echo "  - FirstUnique"
    echo "  - DistinctSubstringLength"
    echo "  - TopFrequents"
    echo "  - IsAnagram"
    echo "  - LongestCommonPrefix"
    echo "  - HarmoniousFusion"
    echo ""
    echo "📁 Group 5:"
    echo "  - PerfectNumber"
    echo "  - SteadySequence"
    echo "  - ValidSudoku"
    echo "  - ArmstrongNumber"
    echo "  - MaximalSquare"
    echo "  - SpiralMatrix"
    echo ""
    echo "Example: $0 HTMLValidator"
    echo "Example: $0 NextPrime"
    exit 1
fi

EXERCISE_NAME=$1

# Find the group for the exercise using simple case statements
find_group() {
    case "$1" in
        # Group 1
        "MultiplicationTable"|"TimeTracker"|"AgeFinder"|"TodoList"|"DayOfWeek"|"MonthlyPeriod")
            echo "g1"
            ;;
        # Group 2
        "HTMLValidator"|"Flexisort"|"NextPrime"|"AlmostPalindrome"|"BreakdownURL"|"ConfigProtector"|"FactorialMaster")
            echo "g2"
            ;;
        # Group 3
        "CircularLinkedList"|"SingletonBlueprint"|"DoubleLinkedList"|"SingleLinkedList"|"FactoryBlueprint"|"BuilderBlueprint")
            echo "g3"
            ;;
        # Group 4
        "FirstUnique"|"DistinctSubstringLength"|"TopFrequents"|"IsAnagram"|"LongestCommonPrefix"|"HarmoniousFusion")
            echo "g4"
            ;;
        # Group 5
        "PerfectNumber"|"SteadySequence"|"ValidSudoku"|"ArmstrongNumber"|"MaximalSquare"|"SpiralMatrix")
            echo "g5"
            ;;
        *)
            echo ""
            ;;
    esac
}

GROUP_DIR=$(find_group "$EXERCISE_NAME")

if [ -z "$GROUP_DIR" ]; then
    echo "❌ Error: Exercise '$EXERCISE_NAME' not found in the exercise list"
    echo ""
    echo "💡 Did you mean one of these?"
    # Simple fuzzy matching
    case "$EXERCISE_NAME" in
        *"HTML"*|*"html"*) echo "  - HTMLValidator (g2)" ;;
        *"Prime"*|*"prime"*) echo "  - NextPrime (g2)" ;;
        *"Sort"*|*"sort"*) echo "  - Flexisort (g2)" ;;
        *"Age"*|*"age"*) echo "  - AgeFinder (g1)" ;;
        *"Todo"*|*"todo"*) echo "  - TodoList (g1)" ;;
    esac
    echo ""
    echo "Run '$0' without arguments to see all available exercises"
    exit 1
fi

# Get the absolute path to the script directory
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Check if group directory exists
if [ ! -d "$SCRIPT_DIR/$GROUP_DIR" ]; then
    echo "❌ Error: Group directory '$SCRIPT_DIR/$GROUP_DIR' does not exist"
    echo "💡 You may need to create it: mkdir $SCRIPT_DIR/$GROUP_DIR"
    exit 1
fi

# Check if exercise directory exists
EXERCISE_PATH="$SCRIPT_DIR/$GROUP_DIR/$EXERCISE_NAME"
if [ ! -d "$EXERCISE_PATH" ]; then
    echo "❌ Error: Exercise directory '$EXERCISE_PATH' does not exist"
    echo "💡 Available exercises in $SCRIPT_DIR/$GROUP_DIR:"
    ls -1 "$SCRIPT_DIR/$GROUP_DIR" | grep -v "\.sh\|\.md\|\.tar\|build" | head -10
    exit 1
fi

# Check if the exercise has Java files
if [ $(ls "$EXERCISE_PATH"/*.java 2>/dev/null | wc -l) -eq 0 ]; then
    echo "❌ Error: No Java files found in $EXERCISE_PATH"
    exit 1
fi

echo "🧪 Testing $EXERCISE_NAME..."
echo "📁 Group: $GROUP_DIR"
echo "📂 Path: $EXERCISE_PATH"
echo "🐳 Running Docker test..."
echo ""

# Store current directory to return to later
ORIGINAL_DIR="$(pwd)"

# Run the Docker test from the group directory
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR/$GROUP_DIR"
docker run --rm \
    -e EXERCISE="$EXERCISE_NAME" \
    -v "$(pwd)":/app/student \
    -w /app \
    ghcr.io/01-edu/test-java

# Check the exit code
if [ $? -eq 0 ]; then
    echo ""
    echo "✅ $EXERCISE_NAME test passed!"
else
    echo ""
    echo "❌ $EXERCISE_NAME test failed!"
    echo ""
    echo "💡 To debug locally, compile and run your solution:"
    echo "   cd $EXERCISE_PATH"
    echo "   javac *.java -d build"
fi

# Return to original directory
cd "$ORIGINAL_DIR"