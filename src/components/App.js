import React, { Component } from "react";
import Header from "./shared/Header";

const pages = [
  { Name: "I Follow", Path: "/ifollow" },
  { Name: "Follow Me", Path: "/followme" },
  { Name: "Following Delta", Path: "/delta" }
];

class App extends Component {
  render() {
    return (
      <div>
        <Header dark={false} pages={pages}>
          Follower Tracker
        </Header>
      </div>
    );
  }
}

export default App;
