# Branch Naming Convention

This convention defines a consistent branch naming scheme to ensure clarity, traceability, and support for automation.

## Format

```
<type>/<ticket-number>-<short-description>
```

Example:
```
feature/1234-login-form
```

## Branch Types

- **feature**: New functionality
- **bugfix**: Bug fixes
- **hotfix**: Critical fixes, e.g., in production
- **release**: Release preparation
- **chore**: Technical tasks, refactoring, cleanup
- **docs**: Documentation changes
- **test**: Tests or test infrastructure

## Guidelines

- Use only lowercase letters, numbers, hyphens (`-`), and slashes (`/`).
- Do not use spaces or special characters (`~`, `^`, `?`, `*`, `:`, etc.).
- The branch name should clearly and concisely describe the purpose of the changes.
- Include a ticket number (e.g., from Jira) if available.
- Keep the description short but meaningful (ideally max. 60 characters).

## Example with Ticket Number

A ticket `APP-2345` describes the implementation of a login form:

```
feature/APP-2345-login-form
```

