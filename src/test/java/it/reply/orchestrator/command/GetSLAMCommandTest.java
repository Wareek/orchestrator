/*
 * Copyright © 2015-2017 Santer Reply S.p.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.reply.orchestrator.command;

import static org.junit.Assert.*;

import it.reply.orchestrator.config.properties.OidcProperties;
import it.reply.orchestrator.config.properties.SlamProperties;
import it.reply.orchestrator.dal.repository.OidcTokenRepository;
import it.reply.orchestrator.dto.RankCloudProvidersMessage;
import it.reply.orchestrator.service.SlamServiceImpl;
import it.reply.orchestrator.service.commands.GetSlam;
import it.reply.orchestrator.workflow.RankCloudProvidersWorkflowTest;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.executor.ExecutionResults;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.net.URI;

public class GetSLAMCommandTest extends BaseRankCloudProviderCommandTest<GetSlam> {

  @InjectMocks
  private GetSlam getSLAMCommand;

  @Spy
  @InjectMocks
  private SlamServiceImpl slamService;

  @Spy
  private SlamProperties slamProperties;

  @Spy
  private OidcProperties oidcProperties;

  @Mock
  private OidcTokenRepository tokenRepository;

  private final String endpoint = "https://www.endpoint.com";

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    slamProperties.setUrl(URI.create(endpoint));
  }

  @Override
  protected GetSlam getCommand() {
    return getSLAMCommand;
  }

  @Test
  public void doexecuteSuccesfully() throws Exception {

    RankCloudProvidersWorkflowTest.mockSlam(mockServer, slamProperties.getUrl());

    RankCloudProvidersMessage message = new RankCloudProvidersMessage();
    message.setDeploymentId(getDeploymentId());
    
    ExecutionResults er = executeCommand(message);

    assertEquals(true, commandSucceeded(er));
  }

}
