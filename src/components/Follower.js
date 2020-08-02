import React, { Component } from "react";
import { GetTestData } from "../services/TestService";
class Follower extends Component {
  state = {
    Data: []
  };

  componentDidMount() {
    GetTestData().then(res =>
      this.setState({
        Data: res
      })
    );
  }

  render() {
    return (
      <div>
        {this.state.Data.map((item, key) => (
          <div class="row">
            <div class="col-sm-6"></div>
            <div className="card">
              <div className="card-header">{item.login}</div>
              <div className="card-body">
                <div className="card-text">
                  <p>{item.id}</p>
                  <p>{item.type}</p>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    );
  }
}

export default Follower;
