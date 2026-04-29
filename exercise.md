# Exercise: Writing Unit Tests and Setting Up CI Pipeline

## Objective

The goal of this exercise is to:

1. Write unit tests for the endpoints in `CargoController` using `MockMvc`.
2. Write unit tests for `CargoService` using `Mockito`.
3. Set up a CI pipeline using GitHub Actions to automatically build and run all tests when code is committed to the `main` branch.

---

## Part 1: Writing Unit Tests for `CargoController`

### Instructions:

1. Use `MockMvc` to test the REST API endpoints in `CargoController`.
2. Ensure that your tests cover different scenarios, including success cases and error handling.
3. Use `@WebMvcTest` to load only the web layer and avoid loading the entire Spring application context.
4. Mock dependencies such as `CargoService` to isolate controller testing.
5. Use assertions to verify HTTP status codes, response bodies, and expected behaviors.


## Part 2: Writing Unit Tests for `CargoService`

### Instructions:

1. Use `Mockito` to mock dependencies of `CargoService`, such as repository classes.
2. Write unit tests that verify the behavior of service methods under different conditions.
3. Ensure proper exception handling is tested.
4. Use `@ExtendWith(MockitoExtension.class)` to enable Mockito in your test class.
5. Verify interactions between the service and its dependencies using `verify()`.


## Part 3: Setting Up a CI Pipeline with GitHub Actions

### Instructions:

1. Create a `.github/workflows/ci.yml` file to define a GitHub Actions workflow.
2. The pipeline should:
    - Trigger on `push` and `pull_request` events targeting the `main` branch.
    - Set up a Java environment using GitHub Actions.
    - Build the project using Maven.
    - Run all unit tests.
3. Ensure that the pipeline fails if any test cases do not pass.
4. Optionally, configure the workflow to cache Maven dependencies for efficiency.

