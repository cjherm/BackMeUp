# BackMeUp

_A fast and flexible backup tool written in Kotlin — built for developers who value simplicity and control._

---

## Features

- BackUp strategy based on diff backups
- Create one single huge full backup and then save HDD space with diffs
- Can back up multiple directories and store them in multiple backups
- Based on all diffs you can choose what state you want to fully restore
- This tool does not remove/modify any of your data
- Good test coverage

---

## Tech Stack

- [Kotlin](https://kotlinlang.org/)
- [Gradle](https://gradle.org/) with Kotlin DSL

---

## Installation

WORK IN PROGRESS, NO SAFE USAGE POSSIBLE AT THE MOMENT

### Usage

#### Create initial full backup:

```
-init -save path -storage path [-save path] [-storage path]
```

Add one or more path/s of the non-empty directories you want to save. Add one or more path/s to empty directories you
want to store you backups in.

#### Create backup only of changes to specific source

```
-diff -src path -storage path [-storage path]
```

Add the path non-empty directories you want to save. Add one or more path/s to empty directories you want to store you
backups in.

#### Restore file system from specific backup

```
-restore -from path -to path
```

sdsadsas