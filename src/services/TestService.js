export const GetTestData = () =>
  fetch("/api/test.json").then(res => res.json());
