export const GetUserFollowers = (userID, page) =>
  fetch(
    `https://api.github.com/users/${userID}/followers?page${page}`
  ).then(res => res.json());

export const GetUserFollowing = (userID, page) =>
  fetch(`https://api.github.com/users/${userID}/following?${page}`).then(res =>
    res.json()
  );
