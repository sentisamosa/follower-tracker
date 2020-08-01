import React, { Component } from "react";
import Header from "./shared/Header";

const pages = [
  { Name: "I Follow", Path: "/ifollow" },
  { Name: "Follow Me", Path: "/followme" },
  { Name: "Following Delta", Path: "/delta" }
];

class App extends Component {
  //navigationItems = ['Follower Tracker', 'I Follow', 'Follow Me', "Doesn't Follow Me"];

  render() {
    return (
      <div>
        <Header dark={true}>Follower Tracker</Header>
      </div>
    );
  }
}

export default App;
