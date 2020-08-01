import React, { Component } from "react";
import Header from "./shared/Header";

class App extends Component {
  navigationItems = ['Follower Tracker', 'I Follow', 'Follow Me', "Doesn't Follow Me"];

  render() {
    return (
      <div>
        <Header dark={true}>{this.navigationItems}</Header>
      </div>
    );
  }
}

export default App;
