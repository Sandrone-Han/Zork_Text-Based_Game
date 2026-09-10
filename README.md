# Zork Text Adventure Engine

A data-driven Java text-adventure engine built around commands, world-state objects, factories, and game events. The bundled mystery contains ten connected rooms and is loaded from validated JSON rather than being hard-coded in Java.

## Current status

- Java 21
- Maven Wrapper for repeatable Windows, Linux, and macOS builds
- 118 automated tests
- Strict UTF-8 JSON game-data validation
- GitHub Actions build and test workflow
- Commands for movement, inspection, inventory, equipment use, item combination, help, and quitting

The project is currently completing its engine-foundation phase. Recipe-driven crafting, explicit win/lose states, save/load, and a longer polished vertical slice are planned next.

## Requirements

- JDK 21
- `JAVA_HOME` pointing to that JDK

Maven does not need to be installed; the checked-in Maven Wrapper downloads the declared Maven version automatically.

## Run

Windows PowerShell:

```powershell
.\mvnw.cmd compile exec:java
```

Linux or macOS:

```sh
sh ./mvnw compile exec:java
```

Load a different game-data file:

```powershell
.\mvnw.cmd compile exec:java -Dexec.args="data/game.json"
```

## Test

Windows:

```powershell
.\mvnw.cmd test
```

Linux or macOS:

```sh
sh ./mvnw test
```

## Commands

```text
look
look room
look exits
move north
get key
drop key
use key on chest
combine item1 with item2
status inventory
help move
quit
```

## Game-data format

The default world is defined in [`data/game.json`](data/game.json). Descriptions may safely contain commas and punctuation.

```json
{
  "player": "Hero",
  "initialRoomId": "hall",
  "rooms": [
    {
      "id": "hall",
      "name": "Entrance Hall",
      "description": "A cold, silent hall.",
      "items": [],
      "containers": [],
      "equipment": [],
      "exits": []
    }
  ]
}
```

At startup, the loader rejects invalid content with the file path and precise field location. Validation includes:

- missing or incorrectly typed fields
- duplicate room and object IDs
- an unknown initial room
- exits targeting unknown rooms
- equipment targeting or producing unknown IDs
- malformed JSON with line and column numbers

Optional arrays can be omitted. The optional `hidden` field defaults to `false`.

## Architecture

```text
Game
  -> GameEngine
       -> Tokeniser -> Parser -> CommandFactory -> Command
       -> GameState -> Map / Room / Player / Objects
       -> EventManager -> Observers

GameStateFileParser
  -> validates JSON
  -> constructs the initial GameState
```

Important packages:

- `commands`: command implementations and command creation
- `engine`: game loop orchestration
- `events`: domain event notification
- `gameobjects`: world and inventory model
- `parser`: user-input tokenisation and parsing
- `utils`: validated JSON loading

## Adding content

1. Add the room or object to `data/game.json`.
2. Give every room and object a unique, non-empty ID.
3. Ensure every exit points to an existing room.
4. Ensure equipment `use.target` and `use.result` reference existing room or object IDs.
5. Run `.\mvnw.cmd test` before committing.

## Roadmap

- recipe-driven combinations
- conditional exits and reusable world flags
- explicit win and loss states
- save/load and undo
- full-playthrough integration tests
- downloadable release and gameplay video
