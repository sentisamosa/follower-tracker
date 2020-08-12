import React, { Component } from "react";
import { GetUserFollowers } from "../services/GitHubService";
class Follower extends Component {
  state = {
    FollowerData: [],
    Page: 1
  };

  componentDidMount() {
    GetUserFollowers("kshtj24", this.state.Page).then(res =>
      this.setState({
        FollowerData: res,
        Page: 1
      })
    );
  }

  handleNextClick() {
    var pageNum = this.state.Page + 1;
    GetUserFollowers("kshtj24", pageNum).then(res =>
      this.setState({
        FollowerData: res,
        Page: pageNum
      })
    );
  }

  handlePreviousClick() {
    if (this.state.Page > 1) {
      var pageNum = this.state.Page - 1;

      GetUserFollowers("kshtj24", pageNum).then(res =>
        this.setState({
          FollowerData: res,
          Page: pageNum
        })
      );
    }
  }

  render() {
    return (
      <div className="row">
        {this.state.FollowerData.map((item, key) => (
          <div className="col-sm-4 my-3" key={key}>
            <div className="card">
              <div className="card-body">
                <div className="card-text">
                  <img
                    src={item.avatar_url}
                    alt={item.key}
                    className="img-thumbnail img-fluid w-50 float-left mr-2"
                  />
                  <p>{item.login}</p>
                  <a
                    href={item.html_url}
                    className="btn btn-outline-primary"
                    role="button"
                  >
                    View
                  </a>
                  <a
                    href="followme#"
                    className="btn btn-outline-primary ml-1"
                    role="button"
                  >
                    Follow
                  </a>
                </div>
              </div>
            </div>
          </div>
        ))}
        <div className="container fixed-bottom mb-2 text-right">
          <button
            className="btn btn-dark btn-sm"
            onClick={this.handlePreviousClick}
          >
            Previous
          </button>
          <button
            className="btn btn-dark btn-sm ml-3"
            onClick={this.handleNextClick}
          >
            Next
          </button>
        </div>
      </div>
    );
  }
}

export default Follower;
