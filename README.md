# se_project

repository for ZJU 2025 SE project of some group

## Project Introduction

TODO

## Commit

On commit, please follow the [commit message convention](https://www.conventionalcommits.org/en/v1.0.0/) so that [Github Actions: Update Changelog](https://github.com/Hzeristo/se_project/actions/workflows/changelog.yml) can generate changelog automatically.

```
The commit contains the following structural elements, to communicate intent to the consumers of your library:

fix: a commit of the type fix patches a bug in your codebase (this correlates with PATCH in Semantic Versioning).

feat: a commit of the type feat introduces a new feature to the codebase (this correlates with MINOR in Semantic Versioning).

BREAKING CHANGE: a commit that has a footer BREAKING CHANGE:, or appends a ! after the type/scope, introduces a breaking API change (correlating with MAJOR in Semantic Versioning). A BREAKING CHANGE can be part of commits of any type.

types other than fix: and feat: are allowed, for example @commitlint/config-conventional (based on the Angular convention) recommends build:, chore:, ci:, docs:, style:, refactor:, perf:, test:, and others.

footers other than BREAKING CHANGE: <description> may be provided and follow a convention similar to git trailer format.
```

## Branchs

1. PR: Collaborators should work on their personal own branch, and then create a PR to merge into the main branch.
2. Auto CI: On merge, Github Actions should automatically run the unit test. Collaborators should provide a description of how to run the unit test in ubuntu environment and how to get test report files.

## Test

1. Unit test: Collaborators should write unit tests for their code. Unit test should cover all cases the code can handle. On merge, the unit test should be run automatically by Github Actions(TODO).
2. Integration test: Integration test is to be done when code is merged into main branch. TODO
3. Perfomance test: Use specialized tools and update tetsing results in `/docs`

## Progress

### Project Setup

- [x] project init
- [x] wihteboard init
- [x] github actions for auto log
- [ ] github actions for auto CI
- [ ] github actions for auto DD

### Design

TODO

### Code

TODO

### Test

TODO