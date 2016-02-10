import React, { Component } from 'react';
import LoggedOut from '../LoggedOut';

import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import * as userActions from '../../actions/UserActions';

class LoggedOutContainer extends Component {
  render() {
    const { children } = this.props;

    return (
      <LoggedOut
        children={children}
        onLogin={this.onLogin.bind(this)} />
    );
  }

  onLogin(username, password) {
    this.props.userActions.login(username, password);
  }
}

export default connect(
  state => ({}),
  dispatch => ({
    userActions: bindActionCreators(userActions, dispatch)
  })
)(LoggedOutContainer);
