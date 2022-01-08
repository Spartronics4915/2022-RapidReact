# Spartronics 4915's Robot Template

## Setup

This repository is marked as a "Template" on Github, which allows you to create a new codebase from the latest snapshot of this code.
Use it to create a new repository under the Spartronics4915 organization titled `XXXX-GameName`, where `XXXX` is the current year.
Whether the repository is public (visible to other teams) or private (only visible to members of the Spartronics4915 organization) is up to you, but in the spirit of Coopertition, it should be made public at the end of the season regardless.

After creating the new season's codebase, the following four changes must be made:

- Update the current year in `wpilib_preferences.json`.
- Update the current year in `settings.gradle`.
- Refactor the `frc` folder into `frcXXXX`, where `XXXX` is the current year.
- Change `ROBOT_MAIN_CLASS` in `build.gradle` to point to the new `Main` class: `com.spartronics4915.frcXXXX.Main`.

Additionally, the following steps are advised:

- Copy the contents of `SpartronicsLib` into the new codebase to facilitate [simultaneous development](https://github.com/Spartronics4915/SpartronicsLib#for-spartronics).
  - This will override the Jitpack setup, so no need to do anything there.
- Update vendor dependencies (the WPILib extension provides an option for this).
- Remove this "Setup" section from the README.

The `ExampleSubsystem` and `ExampleCommand` classes are non-functional and can
be left in as a reference or taken out at your discretion.

## Usage

### Prerequisites

Ensure you have Java 11 installed on your computer and added to `$PATH`.
Visual Studio Code, Git, and the WPILib extension are also helpful for development.

The (highly) recommended way to install these is through [the WPILib installer](https://docs.wpilib.org/en/latest/docs/zero-to-robot/step-2/wpilib-setup.html
).

### Installation

Once your development environment is set up, `clone` this repository to your computer.

Run `./gradlew tasks` to list available Gradle options.

Run `./gradlew build` or use the WPILib extension's `Build Robot Code` option to build or compile the codebase.

Run `./gradlew deploy` or use the WPILib extension's `Deploy Robot Code` option while connected to the robot to build and deploy your code.

## Style Guide

- **Indentation**: Four spaces, no tabs.
- **Braces**: Each brace goes on its own line.
  - This is verbose, but intentionally so - brace errors are common,
    often difficult-to-diagnose, and have caused problems at bad times in the past.
- Line length: Eighty characters or less, as a rule of thumb.
  - This improves legibility and makes it easier to view files side-by-side.
  - The formatter doesn't actually enforce this until lines are 120 characters,
    to give you flexibility around how you'd like to wrap your lines.
- Variable names: `camelCase`.
  - **Member variables**: Prefix with `m`, ex. `mMemberVariable`.
  - **Final constants**: Prefix with `k`, ex. `kFinalConstant`.
  - Parameters: No prefix.
- Class and file names: `PascalCase`.
- Folder names: `lowercase`.
- **Package structure**: `com.spartronics4915.frcXXXX`

(differences from WPILib are in **bold**)

A relatively unopinionated Eclipse-style formatter is included in the `.settings` folder.
This can be run at any time by opening Visual Studio Code's Command Palette (`Ctrl+Shift+P`) and selecting `Format Document`.
